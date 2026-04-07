package com.dee.ecommerce.order_service.exception;

public class InvalidOrderException extends RuntimeException{

    public InvalidOrderException(String message){
        super(message);
    }
}
