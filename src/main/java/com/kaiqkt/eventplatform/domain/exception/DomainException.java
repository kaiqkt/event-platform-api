package com.kaiqkt.eventplatform.domain.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
public class DomainException extends Exception{

    @Getter
    private ErrorType type;
    private String message;

    public DomainException(ErrorType type) {
        super(type.getMessage());
        this.type = type;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
