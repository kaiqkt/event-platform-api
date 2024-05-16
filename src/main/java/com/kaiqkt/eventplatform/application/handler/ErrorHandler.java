package com.kaiqkt.eventplatform.application.handler;

import com.kaiqkt.eventplatform.domain.exception.DomainException;
import com.kaiqkt.eventplatform.generated.application.dto.ErrorV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
class ErrorHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            FieldError fieldError = (FieldError) error;
            String name = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errors.put(name, message);
        });

        ErrorV1 error = new ErrorV1("INVALID_ARGUMENTS", errors);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorV1> handleDomainException(DomainException ex, WebRequest request) {
        ErrorV1 error = new ErrorV1(ex.getType().name(), ex.getType().getMessage());

        return new ResponseEntity<>(error, HttpStatusCode.valueOf(ex.getType().getCode()));
    }
}
