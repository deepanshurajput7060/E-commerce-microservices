package com.dee.ecommerce.auth_service.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.dee.ecommerce.auth_service.config.JwtUtil;
import com.dee.ecommerce.auth_service.dto.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

	private final KafkaTemplate<String, Object> kafkaTemplate;	
	private final UserRepo userRepo;	
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	@Override
	public AuthResponse login(AuthRequest request) {
		
		log.info("Login attempt for Email: {}", request.getEmail());
		
		// Find user by email
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.getEmail()));

        // Validate password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

		Map<String, Object> claims = new HashMap<>();
		claims.put("userId", user.getId());
		claims.put("roles", user.getRoles());

		String token = jwtUtil.generateToken(user.getEmail(), claims);;

        log.info("Login Successfull for Email: {}", request.getEmail());
        
        // Login successful (JWT token will be added later)
        return new AuthResponse(token, "Bearer");
	}

	@Override
	public ApiResponse register(RegisterRequest request) {
		
		log.info("Register attempt for Email: {}", request.getEmail());

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
	    
	    log.info("Registered successfully for Email: {}", request.getEmail());
	    
	    UserRegisteredEvent event = new UserRegisteredEvent(user.getId(), user.getEmail());
	    kafkaTemplate.send("USER_REGISTERED", event);
	    log.info("USER_REGISTERED event created");
	    
	    return new ApiResponse("Registered successful", true);
	}


}
