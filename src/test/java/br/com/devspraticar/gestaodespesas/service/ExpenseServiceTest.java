package br.com.devspraticar.gestaodespesas.service;

import br.com.devspraticar.gestaodespesas.exception.InternalServerErrorException;
import br.com.devspraticar.gestaodespesas.exception.InvalidInstallmentQuantityException;
import br.com.devspraticar.gestaodespesas.exception.InvalidInstallmentStartDateException;
import br.com.devspraticar.gestaodespesas.model.Expense;
import br.com.devspraticar.gestaodespesas.model.ExpenseInstallment;
import br.com.devspraticar.gestaodespesas.model.InstallmentControl;
import br.com.devspraticar.gestaodespesas.repository.ExpenseInstallmentRepository;
import br.com.devspraticar.gestaodespesas.repository.ExpenseRepository;
import br.com.devspraticar.gestaodespesas.repository.InstallmentControlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @InjectMocks
    private ExpenseService service;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private InstallmentCalculatorService installmentCalculator;

    @Mock
    private ExpenseInstallmentRepository expenseInstallmentRepository;

    @Mock
    private InstallmentControlRepository installmentControlRepository;

    private Expense expense;
    private ExpenseInstallment installment;

    @BeforeEach
    void setup() {
        installment = new ExpenseInstallment();
        installment.setQuantity(3);
        installment.setStartDate(LocalDate.now());

        expense = new Expense();
        expense.setId(1L);
        expense.setExpenseDate(LocalDate.now());
        expense.setAmount(new BigDecimal("100.00"));
        expense.setInstallment(installment);
    }

    @Test
    @DisplayName("Deve criar despesa com parcelamento com sucesso")
    void shouldCreateExpenseWithInstallments() {
        //Arrange
        var controls = List.of(
            installmentControl(new BigDecimal("33.34")),
            installmentControl(new BigDecimal("33.33")),
            installmentControl(new BigDecimal("33.33"))
        );
        when(expenseRepository.create(any())).thenReturn(expense);
        when(expenseInstallmentRepository.save(any())).thenReturn(installment);
        when(installmentCalculator.calculate(installment, expense.getAmount())).thenReturn(controls);
        //Act
        Expense result = service.create(expense);
        //Assert
        assertThat(result).isNotNull();
        assertThat(result.getInstallment()).isNotNull();
        assertThat(result.getInstallment().getInstallments()).hasSize(3);
        verify(expenseRepository).create(expense);
        verify(expenseInstallmentRepository).save(installment);
        verify(installmentCalculator).calculate(installment, expense.getAmount());
        verify(installmentControlRepository, times(3)).save(any());
    }

    @Test
    @DisplayName("Deve criar despesa sem parcelamento")
    void shouldCreateExpenseWithoutInstallments() {
        //Arrange
        expense.setInstallment(null);
        when(expenseRepository.create(any())).thenReturn(expense);
        //Act
        Expense result = service.create(expense);
        //Assert
        assertThat(result).isNotNull();
        assertThat(result.getInstallment()).isNull();
        verify(expenseRepository).create(expense);
        verifyNoInteractions(expenseInstallmentRepository);
        verifyNoInteractions(installmentCalculator);
        verifyNoInteractions(installmentControlRepository);
    }

    @Test
    @DisplayName("Deve lançar exceção quando quantidade de parcelas for inválida")
    void shouldThrowExceptionWhenInstallmentQuantityIsInvalid() {
        //Arrange
        Expense invalidExpense = mock(Expense.class);
        when(invalidExpense.installmentInvalid()).thenReturn(true);
        //Act & Assert
        assertThatThrownBy(() -> service.create(invalidExpense)).isInstanceOf(InvalidInstallmentQuantityException.class);
        verifyNoInteractions(expenseRepository);
    }

    @Test
    @DisplayName("Deve lançar exceção quando data inicial da parcela for inválida")
    void shouldThrowExceptionWhenInstallmentStartDateIsInvalid() {
        //Arrange
        Expense invalidExpense = mock(Expense.class);
        when(invalidExpense.installmentInvalid()).thenReturn(false);
        when(invalidExpense.isInstallmentStartDateValid()).thenReturn(false);
        //Act & Assert
        assertThatThrownBy(() -> service.create(invalidExpense)).isInstanceOf(InvalidInstallmentStartDateException.class);
        verifyNoInteractions(expenseRepository);
    }

    @Test
    @DisplayName("Deve lançar InternalServerErrorException em erro inesperado")
    void shouldThrowInternalServerErrorOnUnexpectedException() {
        //Arrange
        when(expenseRepository.create(any())).thenThrow(new RuntimeException("DB down"));
        //Act & Arrange
        assertThatThrownBy(() -> service.create(expense)).isInstanceOf(InternalServerErrorException.class);
        verify(expenseRepository).create(expense);
    }

    private InstallmentControl installmentControl(BigDecimal amount) {
        InstallmentControl control = new InstallmentControl();
        control.setAmount(amount);
        return control;
    }

}