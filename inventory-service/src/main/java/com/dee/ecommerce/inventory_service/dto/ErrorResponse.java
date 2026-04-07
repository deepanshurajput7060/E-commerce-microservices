package com.dee.ecommerce.inventory_service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
