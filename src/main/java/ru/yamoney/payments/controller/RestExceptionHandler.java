package ru.yamoney.payments.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yamoney.payments.dto.ApiErrorResponse;
import ru.yamoney.payments.exception.NotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ApiErrorResponse defaultExceptionHandler(Exception ex) {
        return ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .error_code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getLocalizedMessage())
                .detail(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiErrorResponse handleNotFoundException(NotFoundException ex) {
        return ApiErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .error_code(HttpStatus.NOT_FOUND.value())
                .message(ex.getLocalizedMessage())
                .detail(ex.getMessage())
                .build();
    }
}
