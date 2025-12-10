package com.dee.ecommerce.user_service.listeners;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.dee.ecommerce.user_service.entity.User;
import com.dee.ecommerce.user_service.events.UserRegisteredEvent;
import com.dee.ecommerce.user_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserEventListener {

	private static final Logger logger = LoggerFactory.getLogger(UserEventListener.class);
	
	private final UserRepository userRepository;
	
	@KafkaListener (topics = "USER_REGISTERED", groupId = "user-service-group")
	public void handleUserRegistered(UserRegisteredEvent event) {
		
		logger.info("Received USER_REGISTERED event for userId: {}", event.getUserId());
		
		User user = User.builder()
						.userId(event.getUserId())
						.email(event.getEmail())
						.createdAt(LocalDateTime.now())
						.updatedAt(LocalDateTime.now())
						.build();
		
		userRepository.save(user);
		
		logger.info("User created in User Service: {}", event.getUserId());
	}
}
