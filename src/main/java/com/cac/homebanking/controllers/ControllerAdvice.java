package com.cac.homebanking.controllers;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    /*@ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handle(NotFoundException exception, WebRequest webRequest) {
        ErrorMessage errorBody = new ErrorMessage(exception.getMessage(), webRequest);
        return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
    }*/
}
