package com.dee.ecommerce.user_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dee.ecommerce.user_service.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Page<Address> findByUserUserId(String userId, PageRequest pageRequest);
}