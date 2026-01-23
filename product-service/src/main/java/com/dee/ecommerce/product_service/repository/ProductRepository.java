package com.dee.ecommerce.product_service.repository;

import com.dee.ecommerce.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Custom queries like findByNameContainingIgnoreCase(String name);

}
