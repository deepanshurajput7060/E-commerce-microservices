package com.dee.ecommerce.auth_service.service;

import com.dee.ecommerce.auth_service.dto.ApiResponse;
import com.dee.ecommerce.auth_service.dto.AuthRequest;
import com.dee.ecommerce.auth_service.dto.RegisterRequest;

public interface AuthService {
	
	ApiResponse register(RegisterRequest request);
	
	ApiResponse login(AuthRequest request);
}
