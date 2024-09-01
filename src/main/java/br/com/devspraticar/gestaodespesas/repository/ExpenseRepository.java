package br.com.devspraticar.gestaodespesas.repository;

import br.com.devspraticar.gestaodespesas.model.Expense;

public interface ExpenseRepository {

    Expense create(Expense expense);

}
