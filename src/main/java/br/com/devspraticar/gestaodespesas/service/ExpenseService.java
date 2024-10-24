package br.com.devspraticar.gestaodespesas.service;

import br.com.devspraticar.gestaodespesas.exception.InternalServerErrorException;
import br.com.devspraticar.gestaodespesas.exception.InvalidInstallmentQuantityException;
import br.com.devspraticar.gestaodespesas.exception.InvalidInstallmentStartDateException;
import br.com.devspraticar.gestaodespesas.model.Expense;
import br.com.devspraticar.gestaodespesas.model.ExpenseInstallment;
import br.com.devspraticar.gestaodespesas.model.InstallmentControl;
import br.com.devspraticar.gestaodespesas.repository.ExpenseInstallmentRepository;
import br.com.devspraticar.gestaodespesas.repository.ExpenseRepository;
import br.com.devspraticar.gestaodespesas.repository.InstallmentControlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpenseService {

    private static final int AMOUNT_SCALE = 2;
    private static final int START_INCLUSIVE = 0;

    private final ExpenseRepository expenseRepository;
    private final ExpenseInstallmentRepository expenseInstallmentRepository;
    private final InstallmentControlRepository installmentControlRepository;

    @Transactional
    public Expense create(Expense expense) {
        log.info("Criar expense: {}", expense);
        validateExpense(expense);
        return saveExpense(expense);
    }

    public Expense findById(Long id) {
        var expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        populateInstallmentControls(expense);
        return expense;
    }

    private void populateInstallmentControls(Expense expense) {
        ExpenseInstallment installment = expense.getInstallment();
        if (nonNull(expense.getInstallment())) {
            installment.setInstallments(installmentControlRepository.findAllByInstallmentId(installment.getId()));
            expense.setInstallment(installment);
        }
    }

    private void validateExpense(Expense expense) {
        if(expense.installmentInvalid()) {
            throw new InvalidInstallmentQuantityException();
        }
        if(!expense.isInstallmentStartDateValid()) {
            throw new InvalidInstallmentStartDateException();
        }
    }

    private Expense saveExpense(Expense expense) {
        try {
            expense = expenseRepository.create(expense);
            ExpenseInstallment expenseInstallment = saveInstallment(expense.getInstallment(), expense.getId());
            saveInstallmentControl(expenseInstallment, expense.getAmount());
            expense.setInstallment(expenseInstallment);
        } catch (Exception e) {
            log.error("Erro genérico ao tentar criar a expense: {}", expense, e);
            throw new InternalServerErrorException();
        }
        return expense;
    }

    private ExpenseInstallment saveInstallment(ExpenseInstallment expenseInstallment, long expenseId) {
        ExpenseInstallment expenseInstallmentSaved = null;
        if(nonNull(expenseInstallment)) {
            expenseInstallment.setIdExpense(expenseId);
            expenseInstallmentSaved = expenseInstallmentRepository.save(expenseInstallment);
        }
        return expenseInstallmentSaved;
    }

    /**
     * Salva os registros de controle de parcelas de uma despesa (ExpenseInstallment) no banco de dados.
     *
     * <p>Essa função calcula os valores de cada parcela de acordo com o montante total (amount) e a
     * quantidade de parcelas definidas na instância de ExpenseInstallment. O valor base de cada
     * parcela é calculado dividindo o valor total pelo número de parcelas, e qualquer valor
     * remanescente devido ao arredondamento é distribuído entre as primeiras parcelas. Cada parcela
     * é então persistida no repositório de controle de parcelas (installmentControlRepository).</p>
     *
     * @param expenseInstallment Objeto que contém informações sobre a despesa e o número de parcelas.
     * @param amount Valor total da despesa que será parcelado.
     */
    public void saveInstallmentControl(ExpenseInstallment expenseInstallment, BigDecimal amount) {
        if (isValidInstallment(expenseInstallment, amount)) {
            List<InstallmentControl> installments = generateInstallments(expenseInstallment, amount);
            expenseInstallment.setInstallments(installments);
        }
    }

    private boolean isValidInstallment(ExpenseInstallment expenseInstallment, BigDecimal amount) {
        return nonNull(expenseInstallment) && nonNull(amount);
    }

    private List<InstallmentControl> generateInstallments(ExpenseInstallment expenseInstallment, BigDecimal amount) {
        List<InstallmentControl> installments = new ArrayList<>();
        BigDecimal baseInstallment = calculateBaseInstallment(amount, expenseInstallment.getQuantity());
        BigDecimal remainingValue = calculateRemainingValue(amount, baseInstallment, expenseInstallment.getQuantity());

        createInstallments(expenseInstallment, baseInstallment, remainingValue, installments);

        return installments;
    }

    private BigDecimal calculateRemainingValue(BigDecimal amount, BigDecimal baseInstallment, int quantity) {
        return amount.subtract(baseInstallment.multiply(BigDecimal.valueOf(quantity)));
    }

    private BigDecimal calculateBaseInstallment(BigDecimal amount, int quantity) {
        return amount.divide(new BigDecimal(quantity), AMOUNT_SCALE, RoundingMode.HALF_DOWN);
    }

    private void createInstallments(ExpenseInstallment expenseInstallment, BigDecimal baseInstallment, BigDecimal remainingValue, List<InstallmentControl> installments) {
        IntStream.range(START_INCLUSIVE, expenseInstallment.getQuantity())
            .forEach(indexQuantity -> {
                BigDecimal adjustedInstallment = adjustInstallmentValue(baseInstallment, remainingValue, indexQuantity);
                InstallmentControl installmentControl = createInstallmentControl(expenseInstallment, adjustedInstallment, indexQuantity);
                saveInstallment(installmentControl, installments);
            });
    }

    private BigDecimal adjustInstallmentValue(BigDecimal baseInstallment, BigDecimal remainingValue, int indexQuantity) {
        if (indexQuantity < remainingValue.intValue()) {
            return baseInstallment.add(BigDecimal.valueOf(0.01));
        }
        return baseInstallment;
    }

    private void saveInstallment(InstallmentControl installmentControl, List<InstallmentControl> installments) {
        installmentControlRepository.save(installmentControl);
        installments.add(installmentControl);
    }

    private InstallmentControl createInstallmentControl(ExpenseInstallment expenseInstallment, BigDecimal amountExpense,
             int indexQuantity) {
        LocalDate monthPayment = expenseInstallment.getStartDate().plusMonths(indexQuantity);
        return InstallmentControl.builder()
            .amount(amountExpense)
            .protocol(UUID.randomUUID())
            .installmentId(expenseInstallment.getId())
            .monthPayment(monthPayment.toString().substring(0, 7))
            .build();
    }
}