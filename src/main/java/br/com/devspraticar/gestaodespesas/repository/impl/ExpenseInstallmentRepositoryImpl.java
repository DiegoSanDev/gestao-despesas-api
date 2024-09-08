package br.com.devspraticar.gestaodespesas.repository.impl;

import br.com.devspraticar.gestaodespesas.model.ExpenseInstallment;
import br.com.devspraticar.gestaodespesas.repository.ExpenseInstallmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ExpenseInstallmentRepositoryImpl implements ExpenseInstallmentRepository {

    private final JdbcClient jdbcClient;

    @Override
    public ExpenseInstallment save(ExpenseInstallment expenseInstallment) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sqlInsert = """
            INSERT INTO expense_installment (expense_id, quantity, start_date)
            VALUES (:expenseId, :quantity, :startDate)
            """;
        jdbcClient.sql(sqlInsert)
            .param("expenseId", expenseInstallment.getIdExpense())
            .param("quantity", expenseInstallment.getQuantity())
            .param("startDate", expenseInstallment.getStartDate())
            .update(keyHolder);
        expenseInstallment.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return expenseInstallment;
    }
}
