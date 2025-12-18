package com.dee.ecommerce.user_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.dee.ecommerce.user_service.dto.ApiResponse;
import com.dee.ecommerce.user_service.dto.UserProfileRequest;
import com.dee.ecommerce.user_service.dto.UserProfileResponse;
import com.dee.ecommerce.user_service.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping ("/users")
@RequiredArgsConstructor
@Validated
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private final UserService userService;
	
	@GetMapping ("/{userId}")
	public ResponseEntity<UserProfileResponse> getProfile(@PathVariable String userId) {
		
		logger.info("API Request: Get profile for User: {}", userId);		
		UserProfileResponse response = userService.getProfile(userId);		
		return ResponseEntity.ok(response);		
	}
	
	@PutMapping ("/update/{userId}")
	public ResponseEntity<ApiResponse> updateProfile(
											@PathVariable String userId,
											@Valid @RequestBody UserProfileRequest request
											) {
		
		logger.info("API Request: Update profile for userId: {}", userId);
        ApiResponse response = userService.updateProfile(userId, request);
        return ResponseEntity.ok(response);		
	}
}
