package com.dee.ecommerce.order_service.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemRequest {
    @NotNull(message = "Product ID is required")
    Long productId;

    @Min(value = 1, message = "Quantity must be at least 1")
    Integer quantity;

    @NotNull(message = "Price is required")
    @DecimalMin("0.0")
    BigDecimal price;
}
