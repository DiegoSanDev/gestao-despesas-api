package br.com.devspraticar.gestaodespesas.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessageType {

    INVALID_DATE_INSTALLMENT("ERR001", "A data de início do parcelamento deve ser igual ou posterior à data da despesa."),
    INVALID_INSTALLMENT_QUANTITY("ERR002", "A despesa deve ter no mínimo 2 parcelas."),
    GENERIC_ERROR("ERR003", "Erro genérico");

    private final String code;
    private final String description;

}