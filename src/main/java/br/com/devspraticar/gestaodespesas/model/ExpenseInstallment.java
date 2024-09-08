package br.com.devspraticar.gestaodespesas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "installment")
public class ExpenseInstallment {

    @Id
    private long id;
    private int quantity;
    private long idExpense;
    private LocalDate startDate;
    private List<InstallmentControl> installments;
}