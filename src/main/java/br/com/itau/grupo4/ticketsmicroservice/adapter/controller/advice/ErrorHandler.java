package br.com.itau.grupo4.ticketsmicroservice.controller.advice;

import br.com.itau.grupo4.ticketsmicroservice.controller.advice.formatting.ErrorResponse;
import br.com.itau.grupo4.ticketsmicroservice.exception.TicketNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;

@ResponseBody
@ControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler {

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<ErrorResponse> handleBadRequestException(Exception e) {
        var status = HttpStatus.BAD_REQUEST;
        var msg = retrieveMessage(e);
        return buildResponseEntity(status, msg);
    }

    @ExceptionHandler(value = {TicketNotFoundException.class})
    protected ResponseEntity<ErrorResponse> handleNotFoundException(Exception e) {
        var status = HttpStatus.NOT_FOUND;
        var msg = retrieveMessage(e);
        return buildResponseEntity(status, msg);
    }

    private String retrieveMessage(Exception e) {
        return Objects.isNull(e.getCause()) ? e.getLocalizedMessage() : e.getCause().getMessage();
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(HttpStatus status, String msg) {
        return new ResponseEntity<>(new ErrorResponse(status, msg), status);
    }


}