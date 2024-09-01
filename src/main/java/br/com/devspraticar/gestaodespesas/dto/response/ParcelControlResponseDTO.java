package br.com.devspraticar.gestaodespesas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ParcelControlResponseDTO implements Serializable {

    private boolean paid;
    private UUID protocol;
    private BigDecimal amount;
    private String monthPayment;

}
