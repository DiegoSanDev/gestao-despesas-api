package br.com.devspraticar.gestaodespesas.repository.impl;

import br.com.devspraticar.gestaodespesas.mapper.row.ExpenseRowMapper;
import br.com.devspraticar.gestaodespesas.model.Expense;
import br.com.devspraticar.gestaodespesas.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

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

    @Override
    public Optional<Expense> findById(Long id) {
        String sql = """
                SELECT
                    exi.id AS installmentId,
                    exi.quantity AS quantity,
                    exi.start_date AS startSate,
                    exc.name AS categoryType,
                    expm.name AS paymentMethodType,
                    ex.* FROM expense ex
                INNER JOIN expense_category exc ON(exc.id = ex.category_id)
                INNER JOIN payment_method expm ON(expm.id = ex.payment_method_id)
                INNER JOIN expense_installment exi ON(ex.id = exi.expense_id)
                WHERE ex.id = :id
            """;
        return jdbcClient.sql(sql)
            .param("id", id)
            .query(new ExpenseRowMapper())
            .optional();
    }
}