package br.com.devspraticar.gestaodespesas.repository;

import br.com.devspraticar.gestaodespesas.model.Expense;

import java.util.Optional;

public interface ExpenseRepository {

    Expense create(Expense expense);

    Optional<Expense> findById(Long id);

}
