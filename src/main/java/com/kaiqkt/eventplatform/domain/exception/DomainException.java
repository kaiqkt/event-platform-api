package com.kaiqkt.eventplatform.domain.exception;

public class DomainException extends Exception {

    private final ErrorType type;
    private String message;

    public DomainException(ErrorType type) {
        super(type.getMessage());
        this.type = type;
    }

    public ErrorType getType() {
        return type;
    }
}
