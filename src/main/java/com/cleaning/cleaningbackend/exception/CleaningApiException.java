package com.cleaning.cleaningbackend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class CleaningApiException extends RuntimeException {
    private HttpStatus status;

    private String message;

    public CleaningApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public  CleaningApiException (String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
