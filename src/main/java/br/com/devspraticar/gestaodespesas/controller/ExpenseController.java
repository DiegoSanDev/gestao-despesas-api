package br.com.devspraticar.gestaodespesas.controller;

import br.com.devspraticar.gestaodespesas.controller.api.ExpenseAPI;
import br.com.devspraticar.gestaodespesas.dto.request.ExpenseRequestDTO;
import br.com.devspraticar.gestaodespesas.dto.response.ExpenseResponseDTO;
import br.com.devspraticar.gestaodespesas.service.ExpenseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static br.com.devspraticar.gestaodespesas.mapper.ExpenseMapper.toDomain;
import static br.com.devspraticar.gestaodespesas.mapper.ExpenseMapper.toExpenseResponse;

@Tags(@Tag(name = "EXPENSE API"))
@RestController
@RequestMapping("/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController implements ExpenseAPI {

    private final ExpenseService expenseService;

    @PostMapping
    @Override
    public ResponseEntity<ExpenseResponseDTO> createExpense(@RequestBody ExpenseRequestDTO body) {
        var expense = expenseService.create(toDomain(body));
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(expense.getId())
            .toUri();
        return ResponseEntity.created(location).body(toExpenseResponse(expense));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(toExpenseResponse(expenseService.findById(id)));
    }

}