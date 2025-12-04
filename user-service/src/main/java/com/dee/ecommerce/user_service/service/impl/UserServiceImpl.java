package com.dee.ecommerce.user_service.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dee.ecommerce.user_service.dto.ApiResponse;
import com.dee.ecommerce.user_service.dto.UserProfileRequest;
import com.dee.ecommerce.user_service.dto.UserProfileResponse;
import com.dee.ecommerce.user_service.entity.User;
import com.dee.ecommerce.user_service.exception.UserNotFoundException;
import com.dee.ecommerce.user_service.repository.UserRepository;
import com.dee.ecommerce.user_service.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private final UserRepository userRepository;

	@Override
	public ApiResponse updateProfile(String userId, UserProfileRequest request) {
		
		logger.info("Updating user profile: {}", userId);
		
		User user = userRepository.findById(userId)
								.orElseThrow(() -> new UserNotFoundException(userId));
		
		user.setFullName(request.getFullName());
		user.setPhone(request.getPhone());
		user.setGender(request.getGender());
		user.setDob(request.getDob());

		userRepository.save(user);
		
		logger.info("Profile updated successfully for: {}", userId);
		
		return  new ApiResponse("Profile updated successfully", true);
	}

	@Override
	public UserProfileResponse getProfile(String userId) {

	    logger.info("Fetching user profile for userId: {}", userId);

	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new UserNotFoundException(userId));

	    UserProfileResponse response = UserProfileResponse.builder()
	            .userId(user.getUserId())  // fetch from DB
	            .fullName(user.getFullName())
	            .email(user.getEmail())
	            .phone(user.getPhone())
	            .gender(user.getGender())
	            .dob(user.getDob())
	            .createdAt(user.getCreatedAt())
	            .updatedAt(user.getUpdatedAt())
	            .build();

	    logger.info("User profile fetched successfully for userId: {}", userId);

	    return response;
	}
}
