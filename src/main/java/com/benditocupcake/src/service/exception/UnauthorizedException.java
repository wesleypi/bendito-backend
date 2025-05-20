package com.benditocupcake.src.service.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("Unauthorized user");
    }
}
