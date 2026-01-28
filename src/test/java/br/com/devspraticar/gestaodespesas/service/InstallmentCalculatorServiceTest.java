package br.com.devspraticar.gestaodespesas.service;

import br.com.devspraticar.gestaodespesas.model.ExpenseInstallment;
import br.com.devspraticar.gestaodespesas.model.InstallmentControl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InstallmentCalculatorServiceTest {

    private final InstallmentCalculatorService calculator = new InstallmentCalculatorService();

    @Test
    @DisplayName("Deve gerar parcelas com valores iguais quando não há resto")
    void shouldGenerateEqualInstallmentsWhenNoRemainingValue() {
       //Arrange
        ExpenseInstallment installment = installment(3, LocalDate.of(2026, 1, 1));
        BigDecimal totalAmount = new BigDecimal("300.00");
        //Act
        List<InstallmentControl> result = calculator.calculate(installment, totalAmount);
        //Assert
        assertThat(result).hasSize(3);
        assertThat(result).extracting(InstallmentControl::getAmount)
            .containsExactly(
                new BigDecimal("100.00"),
                new BigDecimal("100.00"),
                new BigDecimal("100.00")
            );
    }

    @Test
    @DisplayName("Deve distribuir centavos extras nas primeiras parcelas")
    void shouldDistributeRemainingCentsToFirstInstallments() {
        //Arrange
        ExpenseInstallment installment = installment(3, LocalDate.of(2026, 1, 1));
        BigDecimal totalAmount = new BigDecimal("100.00");
        //Act
        List<InstallmentControl> result = calculator.calculate(installment, totalAmount);
        //Assert
        assertThat(result)
            .extracting(InstallmentControl::getAmount)
            .containsExactly(
                new BigDecimal("33.34"),
                new BigDecimal("33.33"),
                new BigDecimal("33.33")
            );
    }

    @Test
    @DisplayName("A soma das parcelas deve ser igual ao valor total")
    void sumOfInstallmentsMustMatchTotalAmount() {
        //Arrange
        ExpenseInstallment installment = installment(6, LocalDate.of(2026, 1, 1));
        BigDecimal totalAmount = new BigDecimal("199.99");
        //Act
        List<InstallmentControl> result = calculator.calculate(installment, totalAmount);
        //Arrange
        BigDecimal sum = result.stream()
            .map(InstallmentControl::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertThat(sum).isEqualByComparingTo(totalAmount);
    }

    @Test
    @DisplayName("Deve gerar corretamente o monthPayment para cada parcela")
    void shouldGenerateCorrectMonthPayment() {
        //Arrange
        ExpenseInstallment installment = installment(3, LocalDate.of(2026, 1, 15));
        BigDecimal totalAmount = new BigDecimal("300.00");
        //Act
        List<InstallmentControl> result = calculator.calculate(installment, totalAmount);
        //Assert
        assertThat(result)
            .extracting(InstallmentControl::getMonthPayment)
            .containsExactly(
                "2026-01",
                "2026-02",
                "2026-03"
            );
    }

    @Test
    @DisplayName("Cada parcela deve possuir um protocolo único")
    void eachInstallmentMustHaveUniqueProtocol() {
        //Arrange
        ExpenseInstallment installment = installment(5, LocalDate.of(2026, 1, 1));
        BigDecimal totalAmount = new BigDecimal("500.00");
        //Act
        List<InstallmentControl> result = calculator.calculate(installment, totalAmount);
        //Assert
        assertThat(result)
            .extracting(InstallmentControl::getProtocol)
            .doesNotHaveDuplicates();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando ExpenseInstallment for nulo")
    void shouldReturnEmptyListWhenInstallmentIsNull() {
        List<InstallmentControl> result = calculator.calculate(null, new BigDecimal("100.00"));
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando valor total for nulo")
    void shouldReturnEmptyListWhenTotalAmountIsNull() {
        //Arrange
        ExpenseInstallment installment = installment(2, LocalDate.now());
        //Act
        List<InstallmentControl> result = calculator.calculate(installment, null);
        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Todas as parcelas devem manter scale de duas casas decimais")
    void shouldKeepTwoDecimalPlaces() {
        //Arrange
        ExpenseInstallment installment = installment(3, LocalDate.of(2026, 1, 1));
        BigDecimal totalAmount = new BigDecimal("10.00");
        //Act
        List<InstallmentControl> result = calculator.calculate(installment, totalAmount);
        //Assert
        assertThat(result)
            .allSatisfy(installmentControl ->
                assertThat(installmentControl.getAmount().scale()).isEqualTo(2)
            );
    }

    @Test
    void shouldDistributeCentsCorrectlyFor12999In5Installments() {
        //Arrange
        ExpenseInstallment installment = installment(5, LocalDate.now());
        BigDecimal total = new BigDecimal("129.99");
        //Act
        List<InstallmentControl> result = calculator.calculate(installment, total);
        //Assert
        assertThat(result)
            .extracting(InstallmentControl::getAmount)
            .containsExactly(
                new BigDecimal("26.00"),
                new BigDecimal("26.00"),
                new BigDecimal("26.00"),
                new BigDecimal("26.00"),
                new BigDecimal("25.99")
            );
    }

    private ExpenseInstallment installment(int quantity, LocalDate startDate) {
        ExpenseInstallment installment = new ExpenseInstallment();
        installment.setId(1L);
        installment.setQuantity(quantity);
        installment.setStartDate(startDate);
        return installment;
    }

}