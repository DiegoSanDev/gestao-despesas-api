package br.com.devspraticar.gestaodespesas.exception;

import br.com.devspraticar.gestaodespesas.dto.ErrorMessageDTO;
import br.com.devspraticar.gestaodespesas.enums.ErrorMessageType;

import java.io.Serializable;

public class InvalidInstallmentStartDateException extends BaseException implements Serializable {

    public InvalidInstallmentStartDateException() {
        super();
    }

    @Override
    public ErrorMessageDTO getErrorMessage() {
        return ErrorMessageDTO.builder()
            .code(ErrorMessageType.INVALID_DATE_INSTALLMENT.getCode())
            .description(ErrorMessageType.INVALID_DATE_INSTALLMENT.getDescription())
            .build();
    }

}