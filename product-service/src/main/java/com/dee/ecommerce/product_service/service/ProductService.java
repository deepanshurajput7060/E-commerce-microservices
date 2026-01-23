package com.dee.ecommerce.product_service.service;

import com.dee.ecommerce.product_service.dto.ApiResponse;
import com.dee.ecommerce.product_service.dto.ProductCreateRequest;
import com.dee.ecommerce.product_service.dto.ProductResponse;
import com.dee.ecommerce.product_service.dto.ProductUpdateRequest;
import org.springframework.data.domain.Page;

public interface ProductService {
    ApiResponse createProduct(ProductCreateRequest request);
    Page<ProductResponse> getAllProduct(int page, int size, String sortBy);
    ProductResponse getProductById(Long id);
    ProductResponse updateProduct(Long id, ProductUpdateRequest request);
    ApiResponse deleteProduct(Long id);
}
