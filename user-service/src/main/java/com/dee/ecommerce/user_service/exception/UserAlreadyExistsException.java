package com.dee.ecommerce.user_service.exception;



public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String emailOrPhone) {
        super("User already exists with: " + emailOrPhone);
    }
}
