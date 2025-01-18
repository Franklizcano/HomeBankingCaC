package com.cac.homebanking.exception;

import java.time.ZonedDateTime;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorMessage {
    private String message;
    private HttpStatus status;
    private ZonedDateTime timestamp;

    public ErrorMessage(String message, ZonedDateTime timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
    public ErrorMessage(String message, HttpStatus status, ZonedDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }
}