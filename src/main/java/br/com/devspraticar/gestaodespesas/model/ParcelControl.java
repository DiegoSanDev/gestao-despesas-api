package br.com.devspraticar.gestaodespesas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ParcelControl {

    private long parcelId;
    private boolean paid;
    private UUID protocol;
    private BigDecimal amount;
    private String monthPayment;

}
