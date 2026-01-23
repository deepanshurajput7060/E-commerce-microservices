package com.dee.ecommerce.product_service.service.impl;

import com.dee.ecommerce.product_service.dto.ApiResponse;
import com.dee.ecommerce.product_service.dto.ProductCreateRequest;
import com.dee.ecommerce.product_service.dto.ProductResponse;
import com.dee.ecommerce.product_service.dto.ProductUpdateRequest;
import com.dee.ecommerce.product_service.entity.Product;
import com.dee.ecommerce.product_service.exception.ResourceNotFoundException;
import com.dee.ecommerce.product_service.repository.ProductRepository;
import com.dee.ecommerce.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ModelMapper mapper;

    @Override
    public ApiResponse createProduct(ProductCreateRequest request) {
        log.info("Creating Product with name: {}", request.getName());

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();

        repository.save(product);

        log.info("Product created successfully with ID: {}", product.getId());

        return new ApiResponse("Product created successfully", true);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProduct(int page, int size, String sortBy) {
        log.info("Fetching products | page: {}, size: {}, sortBy: {}", page, size, sortBy);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        return repository.findAll(pageable)
                        .map(product -> mapper.map(product, ProductResponse.class));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        log.info("Fetching Product by Id: {}", id);

        Product product = getProductOrThrow(id);
        return mapper.map(product, ProductResponse.class);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductUpdateRequest request) {
        log.info("Updating Product with Id: {}", id);

        Product product = getProductOrThrow(id);

        mapper.map(request, product);
        Product updatedProduct = repository.save(product);
        log.info("Product updated successfully | id={}", id);

        return mapper.map(updatedProduct, ProductResponse.class);
    }

    @Override
    public ApiResponse deleteProduct(Long id) {
        log.info("Deleting Product with Id: {}", id);

        Product product = getProductOrThrow(id);
        repository.delete(product);

        log.info("Product deleted successfully | id={}", id);
        return new ApiResponse("Product deleted successfully", true);
    }

    // Helper method for Fetching Product
    private Product getProductOrThrow(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found | id={}", id);
                    return new ResourceNotFoundException("Product", "Id", id.toString());
                });
        return product;
    }
}
