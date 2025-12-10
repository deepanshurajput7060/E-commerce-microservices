package com.dee.ecommerce.user_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dee.ecommerce.user_service.entity.User;

public interface UserRepository extends JpaRepository<User, String>{

}
