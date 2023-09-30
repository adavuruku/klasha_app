package com.example.klasha.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WebServiceException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final transient Object response;

    public WebServiceException(String message, HttpStatus httpStatus) {
        this(message, httpStatus, null);
    }

    public WebServiceException(String message, HttpStatus httpStatus, Object response) {
        super(message);
        this.httpStatus = httpStatus;
        this.response = response;
    }
}
