package com.dee.ecommerce.inventory_service.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class InventoryResponseDTO {

    private final Long productId;
    private final Integer quantity;
}
