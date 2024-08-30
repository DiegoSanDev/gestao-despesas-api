package br.com.devspraticar.gestaodespesas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parcel")
public class Parcel {

    private boolean toPay;
    private Long idExpense;
    private BigDecimal amount;
    private LocalDate dueDate;

}