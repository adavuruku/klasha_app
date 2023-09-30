package com.example.klasha.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {
    private final transient Object[] args;
    private final HttpStatus status;

    public BadRequestException(String message,Object[] args,HttpStatus status) {
        super(message);
        this.args = args;
        this.status = status;
    }

    public Object[] getArgs() {
        return args;
    }
    public HttpStatus getStatus() {
        return status;
    }
}
