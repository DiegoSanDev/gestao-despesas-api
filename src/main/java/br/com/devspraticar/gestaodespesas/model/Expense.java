package br.com.devspraticar.gestaodespesas.model;

import br.com.devspraticar.gestaodespesas.model.enums.ExpenseCategoryType;
import br.com.devspraticar.gestaodespesas.model.enums.PaymentMethodType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "expense")
public class Expense {

    @Id
    private Long id;
    private BigDecimal amount;
    private String description;
    private List<Parcel> parcels;
    private ExpenseCategoryType categoryType;
    private PaymentMethodType paymentMethodType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}