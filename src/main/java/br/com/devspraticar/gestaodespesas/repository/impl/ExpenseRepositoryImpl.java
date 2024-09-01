package br.com.devspraticar.gestaodespesas.repository.impl;

import br.com.devspraticar.gestaodespesas.model.Expense;
import br.com.devspraticar.gestaodespesas.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ExpenseRepositoryImpl implements ExpenseRepository {

    private final JdbcClient jdbcClient;

    @Override
    public Expense create(Expense expense) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sqlInsert = """
            INSERT INTO expense (category_id, payment_method_id, amount, description, expense_date, created_at)
            VALUES
            (
                (SELECT ec.id FROM expense_category ec WHERE ec.name = :categoryName),
                (SELECT pm.id FROM payment_method pm WHERE pm.name = :paymentMethodName),
                :amount,
                :description,
                :expenseDate,
                :createdAt
            )
            """;
        expense.setCreatedAt(LocalDateTime.now());
        jdbcClient.sql(sqlInsert)
            .param("amount", expense.getAmount())
            .param("createdAt", expense.getCreatedAt())
            .param("description", expense.getDescription())
            .param("expenseDate", expense.getExpenseDate())
            .param("categoryName", expense.getCategoryType().name())
            .param("paymentMethodName", expense.getPaymentMethodType().name())
            .update(keyHolder);
        expense.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return expense;
    }
}