package br.com.devspraticar.gestaodespesas.mapper;

import br.com.devspraticar.gestaodespesas.dto.request.ExpenseParcelRequestDTO;
import br.com.devspraticar.gestaodespesas.dto.response.ExpenseParcelResponseDTO;
import br.com.devspraticar.gestaodespesas.dto.response.ParcelControlResponseDTO;
import br.com.devspraticar.gestaodespesas.model.ExpenseParcel;
import br.com.devspraticar.gestaodespesas.model.ParcelControl;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class ExpenseParcelMapper {

    public static ExpenseParcel toDomain(ExpenseParcelRequestDTO expenseParcelDTO) {
        return ExpenseParcel.builder()
            .quantity(expenseParcelDTO.getQuantity())
            .startDate(expenseParcelDTO.getStartDate())
            .build();
    }

    public static ExpenseParcelResponseDTO toResponse(ExpenseParcel expenseParcel) {
        return ExpenseParcelResponseDTO.builder()
            .id(expenseParcel.getId())
            .quantity(expenseParcel.getQuantity())
            .startDate(expenseParcel.getStartDate())
            .parcels(getParcels(expenseParcel.getParcels()))
            .build();
    }

    private static List<ParcelControlResponseDTO> getParcels(List<ParcelControl> parcels) {

        if(Objects.nonNull(parcels) && !parcels.isEmpty()) {
            List<ParcelControlResponseDTO> parcelsResponse = new ArrayList<>();
            parcels.forEach(parcelControl -> {
                ParcelControlResponseDTO parcelControlDTO = ParcelControlResponseDTO.builder()
                    .amount(parcelControl.getAmount())
                    .protocol(parcelControl.getProtocol())
                    .monthPayment(parcelControl.getMonthPayment())
                    .build();
                parcelsResponse.add(parcelControlDTO);
            });
            return  parcelsResponse;
        }
        return null;
    }

}