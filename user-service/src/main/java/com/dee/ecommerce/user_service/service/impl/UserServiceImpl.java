package com.dee.ecommerce.user_service.service.impl;

import org.springframework.stereotype.Service;

import com.dee.ecommerce.user_service.dto.ApiResponse;
import com.dee.ecommerce.user_service.dto.UserProfileRequest;
import com.dee.ecommerce.user_service.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Override
	public ApiResponse updateProfile(String userId, UserProfileRequest request) {
		
		return null;
	}

	@Override
	public ApiResponse getProfile(String userId) {
		
		return null;
	}

}
