package br.com.devspraticar.gestaodespesas.repository.impl;

import br.com.devspraticar.gestaodespesas.model.ParcelControl;
import br.com.devspraticar.gestaodespesas.repository.ParcelControlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ParcelControlRepositoryImpl implements ParcelControlRepository {

    private final JdbcClient jdbcClient;

    @Override
    public void save(ParcelControl parcelControl) {
        String sqlInsert = """
            INSERT INTO parcel_control (parcel_id, protocol, amount, month_payment)
            VALUES (:parcelId, :protocol, :amount, :monthPayment)
            """;
        jdbcClient.sql(sqlInsert)
            .param("parcelId", parcelControl.getParcelId())
            .param("protocol", parcelControl.getProtocol().toString())
            .param("amount", parcelControl.getAmount())
            .param("monthPayment", parcelControl.getMonthPayment())
            .update();
    }
}
