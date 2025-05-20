package com.benditocupcake.src.service.exception;

public class TimeoutUserConnectionException extends RuntimeException {
    public TimeoutUserConnectionException(String message) {
        super(message);
    }
}
