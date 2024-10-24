package br.com.devspraticar.gestaodespesas.mapper.row;

import br.com.devspraticar.gestaodespesas.enums.ExpenseCategoryType;
import br.com.devspraticar.gestaodespesas.enums.PaymentMethodType;
import br.com.devspraticar.gestaodespesas.model.Expense;
import br.com.devspraticar.gestaodespesas.model.ExpenseInstallment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpenseRowMapper implements RowMapper<Expense> {

    @Override
    public Expense mapRow(ResultSet rs, int rowNum) throws SQLException {
        var expenseInstallment = ExpenseInstallment.builder()
            .id(rs.getLong("installmentId"))
            .quantity(rs.getInt("quantity"))
            .startDate(rs.getDate("startSate").toLocalDate())
            .build();
        return Expense.builder()
            .id(rs.getLong("id"))
            .installment(expenseInstallment)
            .amount(rs.getBigDecimal("amount"))
            .description(rs.getString("description"))
            .paid("S".equals(rs.getString("is_paid")))
            .expenseDate(rs.getDate("expense_date").toLocalDate())
            .categoryType(ExpenseCategoryType.valueOf(rs.getString("categoryType")))
            .paymentMethodType(PaymentMethodType.valueOf(rs.getString("paymentMethodType")))
            .build();
    }

}