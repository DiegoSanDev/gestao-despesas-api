package br.com.devspraticar.gestaodespesas.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ExpenseRepository {

    private final JdbcClient jdbcClient;
}
