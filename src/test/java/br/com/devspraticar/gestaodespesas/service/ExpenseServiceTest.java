package br.com.devspraticar.gestaodespesas.service;

import br.com.devspraticar.gestaodespesas.exception.InvalidInstallmentQuantityException;
import br.com.devspraticar.gestaodespesas.exception.InvalidInstallmentStartDateException;
import br.com.devspraticar.gestaodespesas.model.Expense;
import br.com.devspraticar.gestaodespesas.model.ExpenseInstallment;
import br.com.devspraticar.gestaodespesas.repository.ExpenseInstallmentRepository;
import br.com.devspraticar.gestaodespesas.repository.ExpenseRepository;
import br.com.devspraticar.gestaodespesas.repository.InstallmentControlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @InjectMocks
    private ExpenseService expenseService;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private ExpenseInstallmentRepository expenseInstallmentRepository;

    @Mock
    private InstallmentControlRepository installmentControlRepository;

    private Expense expense;
    private ExpenseInstallment installment;

    @BeforeEach
    void setUp() {
        installment = ExpenseInstallment.builder()
            .id(10L)
            .quantity(3)
            .startDate(LocalDate.now())
            .build();

        expense = Expense.builder()
            .id(1L)
            .amount(new BigDecimal("100.00"))
            .installment(installment)
            .expenseDate(LocalDate.now())
            .build();
    }

    @Test
    void shouldCreateExpenseSuccessfully() {
        //Arrange
        when(expenseRepository.create(any())).thenReturn(expense);
        when(expenseInstallmentRepository.save(any())).thenReturn(installment);
        //Act
        var result = expenseService.create(expense);
        //Assert
        assertEquals(new BigDecimal("100.00"), result.getAmount());
        assertNotNull(result.getInstallment());
        assertEquals(3, result.getInstallment().getInstallments().size());
        verify(expenseRepository).create(expense);
        verify(expenseInstallmentRepository).save(installment);
        verify(installmentControlRepository, times(3)).save(any());
    }

    @Test
    void shouldThrowExceptionWhenInstallmentQuantityIsInvalid() {
        //Arrange
        var invalidExpense = mock(Expense.class);
        when(invalidExpense.installmentInvalid()).thenReturn(true);
        //Act
        assertThrows(InvalidInstallmentQuantityException.class, () -> expenseService.create(invalidExpense));
        //Assert
        verifyNoInteractions(expenseRepository);
    }

    @Test
    void shouldThrowExceptionWhenInstallmentStartDateIsInvalid() {
        //Arrange
        var invalidExpense = mock(Expense.class);
        when(invalidExpense.installmentInvalid()).thenReturn(false);
        when(invalidExpense.isInstallmentStartDateValid()).thenReturn(false);
        //Act
        assertThrows(InvalidInstallmentStartDateException.class, () -> expenseService.create(invalidExpense));
        //Assert
        verifyNoInteractions(expenseRepository);
    }

}