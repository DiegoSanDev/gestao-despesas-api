package br.com.devspraticar.gestaodespesas.dto.response;

import br.com.devspraticar.gestaodespesas.enums.ExpenseCategoryType;
import br.com.devspraticar.gestaodespesas.enums.PaymentMethodType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpenseResponseDTO {

    private long id;
    private BigDecimal amount;
    private String description;
    private LocalDate expenseDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @JsonProperty(value = "category")
    private ExpenseCategoryType categoryType;

    @JsonProperty(value = "paymentMethod")
    private PaymentMethodType paymentMethodType;

    private ExpenseInstallmentResponseDTO expenseInstallment;

}