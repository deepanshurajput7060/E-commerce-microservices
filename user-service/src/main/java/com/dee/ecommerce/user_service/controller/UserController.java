package com.dee.ecommerce.user_service.controller;

import com.dee.ecommerce.user_service.utils.SecurityUtils;
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
@RequestMapping ("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private final UserService userService;

	@GetMapping("/me")
	public ResponseEntity<UserProfileResponse> getProfile() {

		String userId = SecurityUtils.getCurrentUserId();

		return ResponseEntity.ok(userService.getProfile(userId));
	}


	@PutMapping("/me")
	public ResponseEntity<ApiResponse> updateProfile(
			@Valid @RequestBody UserProfileRequest request) {

		String userId = SecurityUtils.getCurrentUserId();
		logger.info("Update profile for logged-in user: {}", userId);

		return ResponseEntity.ok(userService.updateProfile(userId, request));
	}

}
