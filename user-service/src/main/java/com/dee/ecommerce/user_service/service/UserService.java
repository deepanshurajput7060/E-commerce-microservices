package com.dee.ecommerce.user_service.service;

import com.dee.ecommerce.user_service.dto.ApiResponse;
import com.dee.ecommerce.user_service.dto.UserProfileRequest;
import com.dee.ecommerce.user_service.dto.UserProfileResponse;

public interface UserService {
	
	ApiResponse updateProfile(String userId, UserProfileRequest request);
	
	UserProfileResponse getProfile(String userId);
}
