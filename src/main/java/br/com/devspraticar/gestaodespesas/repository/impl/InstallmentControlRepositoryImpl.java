package br.com.devspraticar.gestaodespesas.repository.impl;

import br.com.devspraticar.gestaodespesas.model.InstallmentControl;
import br.com.devspraticar.gestaodespesas.repository.InstallmentControlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class InstallmentControlRepositoryImpl implements InstallmentControlRepository {

    private final JdbcClient jdbcClient;

    @Override
    public void save(InstallmentControl installmentControl) {
        String sqlInsert = """
            INSERT INTO installment_control (installment_id, protocol, amount, month_payment)
            VALUES (:installmentId, :protocol, :amount, :monthPayment)
            """;
        jdbcClient.sql(sqlInsert)
            .param("installmentId", installmentControl.getInstallmentId())
            .param("protocol", installmentControl.getProtocol().toString())
            .param("amount", installmentControl.getAmount())
            .param("monthPayment", installmentControl.getMonthPayment())
            .update();
    }
}
