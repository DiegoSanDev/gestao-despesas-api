package br.com.devspraticar.gestaodespesas.exception;

import br.com.devspraticar.gestaodespesas.dto.ErrorMessageDTO;
import br.com.devspraticar.gestaodespesas.enums.ErrorMessageType;

import java.io.Serializable;

public class InvalidInstallmentQuantityException extends BaseException implements Serializable {

    public InvalidInstallmentQuantityException() {
        super();
    }

    @Override
    public ErrorMessageDTO getErrorMessage() {
        return ErrorMessageDTO.builder()
            .violationDTO(null)
            .code(ErrorMessageType.INVALID_INSTALLMENT_QUANTITY.getCode())
            .description(ErrorMessageType.INVALID_INSTALLMENT_QUANTITY.getDescription())
            .build();
    }

}