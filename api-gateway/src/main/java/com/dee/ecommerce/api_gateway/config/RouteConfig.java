package com.dee.ecommerce.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r
                        .path("/api/users/**")
                        .filters(f -> f
                                .retry(config -> config
                                        .setRetries(3)
                                        .setMethods(HttpMethod.GET, HttpMethod.POST))
                                .circuitBreaker(cb -> cb
                                        .setName("userServiceCB")
                                        .setFallbackUri("forward:/user-fallback")
                                )
                        )
                        .uri("lb://user-service"))
                .route("auth-service", r -> r
                        .path("/api/auth/**")
                        .filters(f -> f
                                .retry(config -> config
                                        .setRetries(3)
                                        .setMethods(HttpMethod.GET, HttpMethod.POST))
                                .circuitBreaker(cb -> cb
                                        .setName("authServiceCB")
                                        .setFallbackUri("forward:/auth-fallback")
                                )
                        )
                        .uri("lb://auth-service"))
//                .route("product-service", r -> r
//                        .path("/api/products/**")
//                        .filters(f -> f
//                                .retry(config -> config
//                                        .setRetries(3)
//                                        .setMethods(HttpMethod.GET, HttpMethod.POST))
//                                .circuitBreaker(cb -> cb
//                                        .setName("productServiceCB")
//                                        .setFallbackUri("forward:/product-fallback"))
//                        )
//                        .uri("lb://product-service"))
//                .route("order-service", r -> r
//                        .path("/api/orders/**")
//                        .filters(f -> f
//                                .retry(config -> config
//                                        .setRetries(3)
//                                        .setMethods(HttpMethod.GET, HttpMethod.POST))
//                                .circuitBreaker(cb -> cb
//                                        .setName("orderServiceCB")
//                                        .setFallbackUri("forward:/order-fallback"))
//                        )
//                        .uri("lb://order-service"))
                .build();
    }
}
