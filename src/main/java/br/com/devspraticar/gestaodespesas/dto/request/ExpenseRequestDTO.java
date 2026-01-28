package br.com.devspraticar.gestaodespesas.dto.request;

import br.com.devspraticar.gestaodespesas.enums.ExpenseCategoryType;
import br.com.devspraticar.gestaodespesas.enums.PaymentMethodType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpenseRequestDTO implements Serializable {

    private BigDecimal amount;
    private String description;
    private LocalDate expenseDate;
    private transient ExpenseInstallmentRequestDTO installment;
    private ExpenseCategoryType categoryType;
    private PaymentMethodType paymentMethodType;

}
