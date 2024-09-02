package br.com.devspraticar.gestaodespesas.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessageType {

    INVALID_DATE_INSTALLMENT("ERR001", "A data de início do parcelamento deve ser igual ou posterior à data da despesa."),
    DIVISION_PRECISION_ERROR("ERR002", "O valor total deve ser divisível pelo número de parcelas sem resultar em erro de precisão."),
    MISSING_REQUIRED_FIELD("ERR003", "Um campo obrigatório está ausente ou nulo."),
    UNAUTHORIZED_ACCESS("ERR004", "Acesso não autorizado."),
    RESOURCE_NOT_FOUND("ERR005", "O recurso solicitado não foi encontrado."),
    GENERIC_ERROR("ERR006", "Erro genérico");

    private final String code;
    private final String description;

}
