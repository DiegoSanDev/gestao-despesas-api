package br.com.devspraticar.gestaodespesas.controller;

import br.com.devspraticar.gestaodespesas.dto.request.ExpenseRequestDTO;
import br.com.devspraticar.gestaodespesas.dto.response.ExpenseResponseDTO;
import br.com.devspraticar.gestaodespesas.model.Expense;
import br.com.devspraticar.gestaodespesas.service.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @InjectMocks
    private ExpenseController controller;

    @Mock
    private ExpenseService expenseService;

    private Expense expense;
    private ExpenseRequestDTO expenseRequestDTO;
    private ExpenseResponseDTO expenseResponseDTO;

    @BeforeEach
    void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        expenseRequestDTO = new ExpenseRequestDTO();
        expenseResponseDTO = new ExpenseResponseDTO();
        expense = new Expense();
        expense.setId(1);
        expenseResponseDTO.setId(1L);
    }

    @Test
    void createExpense_ShouldReturnCreatedStatus() {
        //Arrange
        when(expenseService.create(any())).thenReturn(expense);
        URI expectedLocation = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(expenseResponseDTO.getId())
                .toUri();
        //Act
        ResponseEntity<ExpenseResponseDTO> response = controller.createExpense(expenseRequestDTO);
        //Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedLocation, response.getHeaders().getLocation());
        verify(expenseService, times(1)).create(any());
    }

    @Test
    void findById_ShouldReturnOkStatus() {
        //Arrange
        Long id = 1L;
        when(expenseService.findById(id)).thenReturn(expense);
        ResponseEntity<ExpenseResponseDTO> response = controller.findById(id);
        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(expenseService, times(1)).findById(id);
    }

}