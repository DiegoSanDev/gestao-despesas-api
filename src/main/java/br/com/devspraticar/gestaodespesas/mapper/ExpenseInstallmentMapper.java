package br.com.devspraticar.gestaodespesas.mapper;

import br.com.devspraticar.gestaodespesas.dto.request.ExpenseInstallmentRequestDTO;
import br.com.devspraticar.gestaodespesas.dto.response.ExpenseInstallmentResponseDTO;
import br.com.devspraticar.gestaodespesas.dto.response.InstallmentControlResponseDTO;
import br.com.devspraticar.gestaodespesas.model.ExpenseInstallment;
import br.com.devspraticar.gestaodespesas.model.InstallmentControl;
import lombok.experimental.UtilityClass;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class ExpenseInstallmentMapper {

    public static ExpenseInstallment toDomain(ExpenseInstallmentRequestDTO expenseInstallmentDTO) {
        return ExpenseInstallment.builder()
            .quantity(expenseInstallmentDTO.getQuantity())
            .startDate(expenseInstallmentDTO.getStartDate())
            .build();
    }

    public static ExpenseInstallmentResponseDTO toResponse(ExpenseInstallment expenseInstallment) {
        return ExpenseInstallmentResponseDTO.builder()
            .id(expenseInstallment.getId())
            .quantity(expenseInstallment.getQuantity())
            .startDate(expenseInstallment.getStartDate())
            .installments(getInstallments(expenseInstallment.getInstallments()))
            .build();
    }

    private static List<InstallmentControlResponseDTO> getInstallments(List<InstallmentControl> installments) {

        if(Objects.nonNull(installments) && !installments.isEmpty()) {
            List<InstallmentControlResponseDTO> installmentResponse = new ArrayList<>();
            installments.forEach(installmentControl -> {
                InstallmentControlResponseDTO installmentControlDTO = InstallmentControlResponseDTO.builder()
                    .amount(installmentControl.getAmount().setScale(2, RoundingMode.HALF_UP))
                    .protocol(installmentControl.getProtocol())
                    .monthPayment(installmentControl.getMonthPayment())
                    .build();
                installmentResponse.add(installmentControlDTO);
            });
            return  installmentResponse;
        }
        return null;
    }

}