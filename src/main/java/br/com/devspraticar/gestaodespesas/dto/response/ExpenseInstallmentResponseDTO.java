package br.com.devspraticar.gestaodespesas.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpenseInstallmentResponseDTO implements Serializable {

    private long id;
    private int quantity;
    private LocalDate startDate;
    private List<InstallmentControlResponseDTO> installments;

}
