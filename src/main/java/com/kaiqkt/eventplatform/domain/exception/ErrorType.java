package com.kaiqkt.eventplatform.domain.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {
    PRODUCER_NOT_FOUND("Producer not found", HttpStatus.NOT_FOUND.value()),
    INVALID_VERSION("Invalid version", HttpStatus.BAD_REQUEST.value()),
    CONSUMER_NOT_FOUND("Consumer not found", 404),
    CONSUMER_ALREADY_EXISTS("Consumer already exists", HttpStatus.CONFLICT.value()),
    VERSION_NOT_FOUND("Version not found", HttpStatus.NOT_FOUND.value()),
    INVALID_EVENT("Event data not match with the required schema", HttpStatus.BAD_REQUEST.value());

    private String message;
    private final int code;


    ErrorType(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public ErrorType setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
