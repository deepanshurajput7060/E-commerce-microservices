package com.dee.ecommerce.auth_service.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dee.ecommerce.auth_service.entity.User;

public interface UserRepo extends JpaRepository<User, String>{

	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);
	
}
