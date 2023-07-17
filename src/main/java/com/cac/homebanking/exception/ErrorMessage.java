package com.cac.homebanking.exception;

import lombok.Data;
import org.springframework.web.context.request.WebRequest;

@Data
public class ErrorMessage {
    String message;
    WebRequest request;
    public ErrorMessage(String message, WebRequest request) {
        this.message = message;
        this.request = request;
    }
}