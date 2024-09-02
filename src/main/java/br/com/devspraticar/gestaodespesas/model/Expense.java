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

import static java.util.Objects.nonNull;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "expense")
public class Expense {

    @Id
    private long id;
    private boolean paid;
    private BigDecimal amount;
    private String description;
    private ExpenseParcel parcel;
    private LocalDate expenseDate;
    private ExpenseCategoryType categoryType;
    private PaymentMethodType paymentMethodType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public boolean existsParcel() {
        return nonNull(this.parcel);
    }

    public boolean isInstallmentStartDateValid() {
        LocalDate startDate = this.parcel.getStartDate();
        return existsParcel() && (this.getExpenseDate().isEqual(startDate) || this.getExpenseDate().isBefore(startDate));
    }
}