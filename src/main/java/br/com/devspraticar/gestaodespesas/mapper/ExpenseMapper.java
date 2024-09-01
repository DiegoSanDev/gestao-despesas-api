package br.com.devspraticar.gestaodespesas.mapper;

import br.com.devspraticar.gestaodespesas.dto.request.ExpenseParcelRequestDTO;
import br.com.devspraticar.gestaodespesas.dto.request.ExpenseRequestDTO;
import br.com.devspraticar.gestaodespesas.dto.response.ExpenseResponseDTO;
import br.com.devspraticar.gestaodespesas.model.Expense;
import br.com.devspraticar.gestaodespesas.model.ExpenseParcel;
import br.com.devspraticar.gestaodespesas.util.DateUtils;
import lombok.experimental.UtilityClass;

import java.util.Optional;

import static java.util.Objects.nonNull;

@UtilityClass
public class ExpenseMapper {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static Expense toDomain(ExpenseRequestDTO expenseRequestDTO) {
        return Expense.builder()
            .amount(expenseRequestDTO.getAmount())
            .description(expenseRequestDTO.getDescription())
            .expenseDate(expenseRequestDTO.getExpenseDate())
            .categoryType(expenseRequestDTO.getCategoryType())
            .parcel(getExpenseParcel(expenseRequestDTO.getParcel()))
            .paymentMethodType(expenseRequestDTO.getPaymentMethodType())
            .build();
    }

    public static ExpenseResponseDTO toExpenseResponse(Expense expense) {
        return ExpenseResponseDTO.builder()
            .id(expense.getId())
            .amount(expense.getAmount())
            .expenseDate(expense.getExpenseDate())
            .description(expense.getDescription())
            .categoryType(expense.getCategoryType())
            .paymentMethodType(expense.getPaymentMethodType())
            .createdAt(DateUtils.formatLocalDateTime(expense.getCreatedAt(), DATE_FORMAT))
            .updatedAt(DateUtils.formatLocalDateTime(expense.getUpdatedAt(), DATE_FORMAT))
            .expenseParcel(nonNull(expense.getParcel()) ? ExpenseParcelMapper.toResponse(expense.getParcel()) : null)
            .build();
    }

    private ExpenseParcel getExpenseParcel(ExpenseParcelRequestDTO expenseParcelDTO) {
        return Optional.ofNullable(expenseParcelDTO)
            .map(ExpenseParcelMapper::toDomain)
            .orElse(null);
    }

}