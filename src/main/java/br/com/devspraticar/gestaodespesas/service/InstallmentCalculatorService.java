package br.com.devspraticar.gestaodespesas.service;

import br.com.devspraticar.gestaodespesas.model.ExpenseInstallment;
import br.com.devspraticar.gestaodespesas.model.InstallmentControl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.util.Objects.nonNull;

@Service
public class InstallmentCalculatorService {

    private static final int AMOUNT_SCALE = 2;
    private static final int START_INDEX = 0;

    public List<InstallmentControl> calculate(ExpenseInstallment expenseInstallment, BigDecimal totalAmount) {
        if (!isValid(expenseInstallment, totalAmount)) {
            return List.of();
        }
        BigDecimal baseInstallment = calculateBaseInstallment(totalAmount, expenseInstallment.getQuantity());
        BigDecimal remainingValue = calculateRemainingValue(totalAmount, baseInstallment, expenseInstallment.getQuantity());
        return generateInstallments(expenseInstallment, baseInstallment, remainingValue);
    }

    private boolean isValid(ExpenseInstallment expenseInstallment, BigDecimal totalAmount) {
        return nonNull(expenseInstallment) && nonNull(totalAmount);
    }

    private BigDecimal calculateBaseInstallment(BigDecimal amount, int quantity) {
        return amount.divide(BigDecimal.valueOf(quantity), AMOUNT_SCALE, RoundingMode.DOWN);
    }

    private BigDecimal calculateRemainingValue(BigDecimal amount, BigDecimal baseInstallment, int quantity) {
        return amount.subtract(baseInstallment.multiply(BigDecimal.valueOf(quantity)));
    }

    private List<InstallmentControl> generateInstallments(ExpenseInstallment expenseInstallment,
            BigDecimal baseInstallment,
            BigDecimal remainingValue) {
        List<InstallmentControl> installments = new ArrayList<>();
        IntStream.range(START_INDEX, expenseInstallment.getQuantity())
            .forEach(index -> {
                BigDecimal installmentAmount = adjustInstallmentValue(baseInstallment, remainingValue, index);
                installments.add(buildInstallmentControl(expenseInstallment, installmentAmount, index));
            });

        return installments;
    }

    private BigDecimal adjustInstallmentValue(BigDecimal baseInstallment, BigDecimal remainingValue, int index) {
        long remainingCents = remainingValue.movePointRight(2).longValueExact();
        if (index < remainingCents) {
            return baseInstallment.add(BigDecimal.valueOf(0.01));
        }
        return baseInstallment;
    }


    private InstallmentControl buildInstallmentControl(ExpenseInstallment expenseInstallment, BigDecimal installmentAmount, int index) {
        LocalDate paymentDate = expenseInstallment.getStartDate().plusMonths(index);

        return InstallmentControl.builder()
            .amount(installmentAmount)
            .protocol(UUID.randomUUID())
            .installmentId(expenseInstallment.getId())
            .monthPayment(YearMonth.from(paymentDate).toString())
            .build();
    }
}