package br.com.devspraticar.gestaodespesas.exception;

import br.com.devspraticar.gestaodespesas.dto.ErrorMessageDTO;

import java.io.Serializable;

public abstract class BaseException extends RuntimeException implements Serializable {

    protected BaseException() {
        this.getErrorMessage();
    }

    public abstract ErrorMessageDTO getErrorMessage();

}