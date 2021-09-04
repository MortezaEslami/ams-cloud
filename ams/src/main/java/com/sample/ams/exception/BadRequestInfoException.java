package com.sample.ams.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestInfoException extends RuntimeException {

    public BadRequestInfoException(String message) {
        super(message);
    }

    public BadRequestInfoException(String message, Throwable throwable) {
        super(message, throwable);
    }
}