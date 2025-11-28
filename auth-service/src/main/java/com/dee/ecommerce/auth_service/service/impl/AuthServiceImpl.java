package com.dee.ecommerce.auth_service.service.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dee.ecommerce.auth_service.dto.ApiResponse;
import com.dee.ecommerce.auth_service.dto.AuthRequest;
import com.dee.ecommerce.auth_service.dto.RegisterRequest;
import com.dee.ecommerce.auth_service.entity.User;
import com.dee.ecommerce.auth_service.events.UserRegisteredEvent;
import com.dee.ecommerce.auth_service.exception.InvalidCredentialsException;
import com.dee.ecommerce.auth_service.exception.ResourceNotFoundException;
import com.dee.ecommerce.auth_service.exception.UserAlreadyExistsException;
import com.dee.ecommerce.auth_service.repository.UserRepo;
import com.dee.ecommerce.auth_service.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);	
	private final KafkaTemplate<String, Object> kafkaTemplate;	
	private final UserRepo userRepo;	
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public ApiResponse login(AuthRequest request) {
		
		logger.info("Login attempt for Email: {}", request.getEmail());
		
		// Find user by email
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.getEmail()));

        // Validate password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        logger.info("Login Successfull for Email: {}", request.getEmail());
        
        // Login successful (JWT token will be added later)
        return new ApiResponse("Login successful", true);
	}

	@Override
	public ApiResponse register(RegisterRequest request) {
		
		logger.info("Register attempt for Email: {}", request.getEmail());

	    if (userRepo.existsByEmail(request.getEmail())) {
	    	throw new UserAlreadyExistsException("User", " Email", request.getEmail());
	    }
	    
	    User user = User.builder()
	    				.name(request.getName())
	    				.email(request.getEmail())
	    				.password(passwordEncoder.encode(request.getPassword()))
	    				.roles(Set.of("ROLE_USER"))
	    				.build();
	    
	    userRepo.save(user);
	    
	    logger.info("Registered successfully for Email: {}", request.getEmail());
	    
	    UserRegisteredEvent event = new UserRegisteredEvent(user.getId(), user.getEmail());
	    kafkaTemplate.send("USER_REGISTERED", event);
	    logger.info("USER_REGISTERED event created");
	    
	    return new ApiResponse("Registered successful", true);
	}


}
