package com.cac.homebanking.controller;

import com.cac.homebanking.exception.BusinessException;
import com.cac.homebanking.exception.ErrorMessage;
import com.cac.homebanking.exception.InsufficientFundsException;
import com.cac.homebanking.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException exception) {
        ErrorMessage errorBody = new ErrorMessage(exception.getMessage(), ZonedDateTime.now());
        return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<ErrorMessage> handleBusinessException(BusinessException exception) {
        ErrorMessage errorBody = new ErrorMessage(exception.getMessage(), exception.getStatus(), ZonedDateTime.now());
        return new ResponseEntity<>(errorBody, exception.getStatus());
    }

    @ExceptionHandler(value = {InsufficientFundsException.class})
    public ResponseEntity<ErrorMessage> handleBusinessException(InsufficientFundsException exception) {
        ErrorMessage errorBody = new ErrorMessage(exception.getMessage(), ZonedDateTime.now());
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }
}
