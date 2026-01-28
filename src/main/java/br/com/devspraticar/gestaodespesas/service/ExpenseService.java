package br.com.devspraticar.gestaodespesas.service;

import br.com.devspraticar.gestaodespesas.exception.InternalServerErrorException;
import br.com.devspraticar.gestaodespesas.exception.InvalidInstallmentQuantityException;
import br.com.devspraticar.gestaodespesas.exception.InvalidInstallmentStartDateException;
import br.com.devspraticar.gestaodespesas.model.Expense;
import br.com.devspraticar.gestaodespesas.model.ExpenseInstallment;
import br.com.devspraticar.gestaodespesas.repository.ExpenseInstallmentRepository;
import br.com.devspraticar.gestaodespesas.repository.ExpenseRepository;
import br.com.devspraticar.gestaodespesas.repository.InstallmentControlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final InstallmentCalculatorService installmentCalculator;
    private final ExpenseInstallmentRepository expenseInstallmentRepository;
    private final InstallmentControlRepository installmentControlRepository;

    @Transactional
    public Expense create(Expense expense) {
        validateExpense(expense);
        return persistExpense(expense);
    }

    private void validateExpense(Expense expense) {
        if (expense.installmentInvalid()) {
            throw new InvalidInstallmentQuantityException();
        }
        if (!expense.isInstallmentStartDateValid()) {
            throw new InvalidInstallmentStartDateException();
        }
    }

    private Expense persistExpense(Expense expense) {
        try {
            Expense savedExpense = expenseRepository.create(expense);
            ExpenseInstallment savedInstallment = persistExpenseInstallment(savedExpense.getInstallment(), savedExpense.getId());
            persistInstallmentControls(savedInstallment, savedExpense.getAmount());
            savedExpense.setInstallment(savedInstallment);
            return savedExpense;
        } catch (Exception e) {
            log.error("Erro genérico ao tentar criar a despesa: {}", expense, e);
            throw new InternalServerErrorException();
        }
    }

    private ExpenseInstallment persistExpenseInstallment(ExpenseInstallment installment, long expenseId) {
        if (nonNull(installment)) {
            installment.setIdExpense(expenseId);
            return expenseInstallmentRepository.save(installment);
        }
        return null;
    }

    private void persistInstallmentControls(ExpenseInstallment expenseInstallment, BigDecimal totalAmount) {
        if (isNull(expenseInstallment) || isNull(totalAmount)) {
            return;
        }
        var installments = installmentCalculator.calculate(expenseInstallment, totalAmount);
        installments.forEach(installmentControlRepository::save);
        expenseInstallment.setInstallments(installments);
    }

}