package com.dee.ecommerce.user_service.exception;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dee.ecommerce.user_service.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler (value = ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handlerUserNotFound(ResourceNotFoundException ex){		
		ApiResponse apiResponse = new ApiResponse(ex.getMessage(), false);		
		return new ResponseEntity<ApiResponse> (apiResponse, HttpStatus.NOT_FOUND);		
	}
	
	@ExceptionHandler (value = UserAlreadyExistsException.class)
	public ResponseEntity<ApiResponse> handlerUserAlreadyExistsException(UserAlreadyExistsException ex){		
		ApiResponse apiResponse = new ApiResponse(ex.getMessage(), false);		
		return new ResponseEntity<ApiResponse> (apiResponse, HttpStatus.CONFLICT);		
	}

}
