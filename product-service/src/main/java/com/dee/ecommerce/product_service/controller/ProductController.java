package com.dee.ecommerce.product_service.controller;

import com.dee.ecommerce.product_service.dto.ApiResponse;
import com.dee.ecommerce.product_service.dto.ProductCreateRequest;
import com.dee.ecommerce.product_service.dto.ProductResponse;
import com.dee.ecommerce.product_service.dto.ProductUpdateRequest;
import com.dee.ecommerce.product_service.service.ProductService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(
            @Valid @RequestBody ProductCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(request));
    }

    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        return ResponseEntity.ok(
                productService.getAllProduct(page, size, sortBy)
        );
    }

    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                productService.getProductById(productId)
        );
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductUpdateRequest request) {

        return ResponseEntity.ok(
                productService.updateProduct(productId, request)
        );
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                productService.deleteProduct(productId)
        );
    }
}
