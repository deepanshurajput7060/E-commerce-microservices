package com.dee.ecommerce.user_service.service;

import com.dee.ecommerce.user_service.dto.ApiResponse;
import com.dee.ecommerce.user_service.dto.UserProfileRequest;

public interface UserService {
	
	ApiResponse updateProfile(String userId, UserProfileRequest request);
	
	ApiResponse getProfile(String userId);
}
