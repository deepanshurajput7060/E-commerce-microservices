package com.dee.ecommerce.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r
                        .path("/users/**")
                        .filters(f -> f
                                .circuitBreaker(cb -> cb
                                        .setName("userServiceCB")
                                        .setFallbackUri("forward:/user-fallback")
                                )
                        )
                        .uri("lb://user-service"))
                .route("auth-service", r -> r
                        .path("/auth/**")
                        .filters(f -> f
                                .circuitBreaker(cb -> cb
                                        .setName("authServiceCB")
                                        .setFallbackUri("forward:/auth-fallback")
                                )
                        )
                        .uri("lb://auth-service"))
                .build();
    }
}
