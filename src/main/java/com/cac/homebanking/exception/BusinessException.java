package com.cac.homebanking.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException{
    private HttpStatus status;
    public BusinessException(String message, HttpStatus status){
        super(message);
    }

    public HttpStatus getStatus() {
        return status;
    }
}
