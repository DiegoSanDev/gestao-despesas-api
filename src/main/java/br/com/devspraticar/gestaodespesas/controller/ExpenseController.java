package br.com.devspraticar.gestaodespesas.controller;

import br.com.devspraticar.gestaodespesas.dto.request.ExpenseRequestDTO;
import br.com.devspraticar.gestaodespesas.dto.response.ExpenseResponseDTO;
import br.com.devspraticar.gestaodespesas.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.devspraticar.gestaodespesas.mapper.ExpenseMapper.toDomain;
import static br.com.devspraticar.gestaodespesas.mapper.ExpenseMapper.toExpenseResponse;

@RestController
@RequestMapping("/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController implements ExpenseAPI {

    private final ExpenseService expenseService;

    @PostMapping
    @Override
    public ResponseEntity<ExpenseResponseDTO> createExpense(@RequestBody ExpenseRequestDTO body) {
        var expense = expenseService.create(toDomain(body));
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(toExpenseResponse(expense));
    }

}