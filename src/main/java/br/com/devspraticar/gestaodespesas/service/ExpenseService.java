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

    private final ExpenseRepository expenseRepository;
    private final ExpenseInstallmentRepository expenseInstallmentRepository;
    private final InstallmentControlRepository installmentControlRepository;

    @Transactional
    public Expense create(Expense expense) {
        log.info("Criar expense: {}", expense);
        validateExpense(expense);
        return saveExpense(expense);
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
        if(nonNull(expenseInstallment)) {
            expenseInstallment.setIdExpense(expenseId);
            return expenseInstallmentRepository.save(expenseInstallment);
        }
        return null;
    }

    private void saveInstallmentControl(ExpenseInstallment expenseInstallment, BigDecimal amount) {
        if(nonNull(expenseInstallment) && nonNull(amount)) {
            List<InstallmentControl> installments  = new ArrayList<>();
            BigDecimal amountInstallment = amount.divide(new BigDecimal(expenseInstallment.getQuantity()), 2, RoundingMode.HALF_UP);
            IntStream.range(0, expenseInstallment.getQuantity())
                .forEach(indexQuantity -> {
                    var installmentControl = getInstallmentControl(expenseInstallment, amountInstallment, indexQuantity);
                    installmentControlRepository.save(installmentControl);
                    installments.add(installmentControl);
                });
            expenseInstallment.setInstallments(installments);
        }
    }

    private InstallmentControl getInstallmentControl(ExpenseInstallment expenseInstallment, BigDecimal amountExpense, int indexQuantity) {
        LocalDate monthPayment = expenseInstallment.getStartDate().plusMonths(indexQuantity);
        return InstallmentControl.builder()
            .amount(amountExpense)
            .protocol(UUID.randomUUID())
            .installmentId(expenseInstallment.getId())
            .monthPayment(monthPayment.toString().substring(0, 7))
            .build();
    }
}