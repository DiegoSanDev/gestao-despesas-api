package br.com.devspraticar.gestaodespesas.repository.impl;

import br.com.devspraticar.gestaodespesas.model.ExpenseParcel;
import br.com.devspraticar.gestaodespesas.repository.ExpenseParcelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ExpenseParcelRepositoryImpl implements ExpenseParcelRepository {

    private final JdbcClient jdbcClient;

    @Override
    public ExpenseParcel save(ExpenseParcel expenseParcel) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sqlInsert = """
            INSERT INTO expense_parcel (expense_id, quantity, start_date)
            VALUES (:expenseId, :quantity, :startDate)
            """;
        jdbcClient.sql(sqlInsert)
            .param("expenseId", expenseParcel.getIdExpense())
            .param("quantity", expenseParcel.getQuantity())
            .param("startDate", expenseParcel.getStartDate())
            .update(keyHolder);
        expenseParcel.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return expenseParcel;
    }
}
