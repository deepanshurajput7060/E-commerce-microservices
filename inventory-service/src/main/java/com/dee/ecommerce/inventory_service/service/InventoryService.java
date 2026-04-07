package com.dee.ecommerce.inventory_service.service;


import com.dee.ecommerce.inventory_service.dto.InventoryRequestDTO;
import com.dee.ecommerce.inventory_service.dto.InventoryResponseDTO;

public interface InventoryService {

    InventoryResponseDTO createOrUpdateStock(InventoryRequestDTO inventoryRequestDTO);

    InventoryResponseDTO getInventory(Long productId);

    boolean isInStock(Long productId, Integer quantity);

    void reduceStock(Long productId, Integer quantity);
}
