package br.com.devspraticar.gestaodespesas.exception;

import br.com.devspraticar.gestaodespesas.dto.ErrorMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class DefaultHandlerExceptionResolver {

    @ExceptionHandler(InvalidInstallmentStartDateException.class)
    public ResponseEntity<ErrorMessageDTO> invalidDateOrderException(InvalidInstallmentStartDateException ex) {
        log.error("invalidDateOrderException: {}", ex.getErrorMessage(), ex);
        return ResponseEntity.unprocessableEntity().body(ex.getErrorMessage());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorMessageDTO> internalServerErrorException(InternalServerErrorException ex) {
        log.error("internalServerErrorException: {}", ex.getErrorMessage(), ex);
        return ResponseEntity.internalServerError().body(ex.getErrorMessage());
    }

    @ExceptionHandler(InvalidInstallmentQuantityException.class)
    public ResponseEntity<ErrorMessageDTO> invalidInstallmentQuantityException(InvalidInstallmentQuantityException ex) {
        log.error("invalidInstallmentQuantityException: {}", ex.getErrorMessage(), ex);
        return ResponseEntity.unprocessableEntity().body(ex.getErrorMessage());
    }

}