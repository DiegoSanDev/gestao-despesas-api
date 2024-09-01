package br.com.devspraticar.gestaodespesas.controller;

import br.com.devspraticar.gestaodespesas.dto.request.ExpenseRequestDTO;
import br.com.devspraticar.gestaodespesas.dto.response.ExpenseResponseDTO;
import org.springframework.http.ResponseEntity;

public interface ExpenseAPI {

    ResponseEntity<ExpenseResponseDTO> createExpense(ExpenseRequestDTO body);

}
