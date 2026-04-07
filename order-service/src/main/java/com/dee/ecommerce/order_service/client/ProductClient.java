package com.dee.ecommerce.order_service.client;


import com.dee.ecommerce.order_service.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/api/products/{productId}")
    ProductResponse getProductById(@PathVariable("productId") Long productId);

    @PutMapping("/api/products/{productId}/reduce")
    void reduceStock(@PathVariable("productId") Long productId,
                     @RequestParam("quantity") Integer quantity);
}
