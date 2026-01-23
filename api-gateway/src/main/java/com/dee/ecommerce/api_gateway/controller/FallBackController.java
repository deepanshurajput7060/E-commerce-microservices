package com.dee.ecommerce.api_gateway.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {

    @GetMapping("/user-fallback")
    public Mono<String> userFallback() {
        return Mono.just("User Service is currently unavailable. Please try later.");
    }

    @GetMapping("/auth-fallback")
    public Mono<String> authFallback() {
        return Mono.just("Auth Service is currently unavailable. Please try later.");
    }

    @GetMapping("/product-fallback")
    public Mono<String> productFallback() {
        return Mono.just("Product Service is currently unavailable. Please try later.");
    }
}
