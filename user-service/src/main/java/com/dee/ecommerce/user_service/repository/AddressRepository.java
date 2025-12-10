package com.dee.ecommerce.user_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dee.ecommerce.user_service.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUserId(String userId);
}