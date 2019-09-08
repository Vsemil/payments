package ru.yamoney.payments.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
class ApiErrorResponse {
    private HttpStatus status;
    private Integer error_code;
    private String message;
    private String detail;
}
