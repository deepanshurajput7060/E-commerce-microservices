package com.dee.ecommerce.product_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private final String message;
    private final int status;
    private final LocalDateTime timestamp;
    private final String path;
}
