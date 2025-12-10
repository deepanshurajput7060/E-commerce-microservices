package com.dee.ecommerce.auth_service.exception;

public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException() {
        super("JWT token has expired");
    }
}
