package com.sample.ams.exception;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@ControllerAdvice
@RestController
public class AmsResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    static final String REQUEST_TIME = "yyyy/MM/dd HH:mm:ss";
    private final MessageSource messageSource;
    /*messageSource.getMessage("validation.notEmpty", null, LocaleContextHolder.getLocale()*/

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse((new SimpleDateFormat(REQUEST_TIME)).format(new Date()),
                        ex.getMessage(),
                        ((ServletWebRequest) request).getHttpMethod().toString(),
                        request.getDescription(false),
                        request.getLocale());

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse((new SimpleDateFormat(REQUEST_TIME)).format(new Date()),
                        ex.getMessage(),
                        ((ServletWebRequest) request).getHttpMethod().toString(),
                        request.getDescription(false),
                        request.getLocale());

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse((new SimpleDateFormat(REQUEST_TIME)).format(new Date()),
                        ex.getMessage(),
                        ((ServletWebRequest) request).getHttpMethod().toString(),
                        request.getDescription(false),
                        request.getLocale());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse((new SimpleDateFormat(REQUEST_TIME)).format(new Date()),
                        ex.getMessage(),
                        ((ServletWebRequest) request).getHttpMethod().toString(),
                        request.getDescription(false),
                        request.getLocale());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Object> handleAllRuntimeExceptions(RuntimeException ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse((new SimpleDateFormat(REQUEST_TIME)).format(new Date()),
                        ex.getMessage().replace("com.sample.ams.", ""),
                        ((ServletWebRequest) request).getHttpMethod().toString(),
                        request.getDescription(false),
                        request.getLocale());
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
