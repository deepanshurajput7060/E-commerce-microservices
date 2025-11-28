package com.dee.ecommerce.auth_service.exception;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super("Invalid or malformed JWT token");
    }
}
