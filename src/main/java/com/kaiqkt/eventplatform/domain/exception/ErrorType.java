package com.kaiqkt.eventplatform.domain.exception;

import lombok.Getter;

@Getter
public enum ErrorType {
    PRODUCER_NOT_FOUND("Producer not found", 404),
    CONSUMER_NOT_FOUND("Consumer not found", 404),
    VERSION_NOT_FOUND("Version not found", 404),
    INVALID_EVENT("Event data is not matching with the required data", 400),
    VERSION_SHOULD_BE_SEQUENTIAL("Version sent does not follow the sequence of other versions or the start version should start with value 1", 400);

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
}
