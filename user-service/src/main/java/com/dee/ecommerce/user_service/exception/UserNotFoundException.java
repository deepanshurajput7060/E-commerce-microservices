package com.dee.ecommerce.user_service.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class UserNotFoundException extends RuntimeException{
	
	public UserNotFoundException(String userId) {
        super("User not found with id: " + userId);
    }
	
	
}
