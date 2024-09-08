package br.com.devspraticar.gestaodespesas.model;

import br.com.devspraticar.gestaodespesas.enums.ExpenseCategoryType;
import br.com.devspraticar.gestaodespesas.enums.PaymentMethodType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "expense")
public class Expense {

    private static final int INSTALLMENT_QUANTITY_MINIMUM = 2;

    @Id
    private long id;
    private boolean paid;
    private BigDecimal amount;
    private String description;
    private ExpenseInstallment installment;
    private LocalDate expenseDate;
    private ExpenseCategoryType categoryType;
    private PaymentMethodType paymentMethodType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public boolean installmentInvalid() {
        return nonNull(this.installment) && this.installment.getQuantity() < INSTALLMENT_QUANTITY_MINIMUM;
    }

    public boolean isInstallmentStartDateValid() {
        if(nonNull(this.installment)) {
            LocalDate startDate = this.installment.getStartDate();
            return this.getExpenseDate().isEqual(startDate) || this.getExpenseDate().isBefore(startDate);
        }
        return true;
    }
}