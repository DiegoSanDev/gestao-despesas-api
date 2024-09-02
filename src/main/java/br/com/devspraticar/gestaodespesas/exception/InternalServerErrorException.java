package br.com.devspraticar.gestaodespesas.exception;

import br.com.devspraticar.gestaodespesas.dto.ErrorMessageDTO;
import br.com.devspraticar.gestaodespesas.enums.ErrorMessageType;

import java.io.Serializable;

public class InternalServerErrorException extends BaseException implements Serializable {

    public InternalServerErrorException() {
        super();
    }

    @Override
    public ErrorMessageDTO getErrorMessage() {
        return ErrorMessageDTO.builder()
            .code(ErrorMessageType.GENERIC_ERROR.getCode())
            .description(ErrorMessageType.GENERIC_ERROR.getDescription())
            .build();
    }
}
