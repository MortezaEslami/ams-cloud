package com.sample.ams.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Locale;

@AllArgsConstructor
@Getter
public class ExceptionResponse {
    private String requestTime;
    private String message;
    private String requestMethod;
    private String uri;
    private Locale locale;

}