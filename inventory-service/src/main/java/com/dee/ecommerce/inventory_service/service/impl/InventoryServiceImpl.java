package com.dee.ecommerce.inventory_service.service.impl;

import com.dee.ecommerce.inventory_service.dto.InventoryRequestDTO;
import com.dee.ecommerce.inventory_service.dto.InventoryResponseDTO;
import com.dee.ecommerce.inventory_service.entity.Inventory;
import com.dee.ecommerce.inventory_service.exception.InsufficientStockException;
import com.dee.ecommerce.inventory_service.exception.InventoryNotFoundException;
import com.dee.ecommerce.inventory_service.repository.InventoryRepository;
import com.dee.ecommerce.inventory_service.service.InventoryService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;

    @Override
    public InventoryResponseDTO createOrUpdateStock(InventoryRequestDTO dto) {
        log.info("Updating inventory for productId: {}", dto.getProductId());

        Inventory inventory = repository.findByProductId(dto.getProductId())
                        .orElse(new Inventory());

        inventory.setProductId(dto.getProductId());
        inventory.setQuantity(dto.getQuantity());

        Inventory saved = repository.save(inventory);
        log.info("Quantity", saved.getQuantity());
        log.info("Inventory updated successfully for productId: {}", saved.getProductId());

        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryResponseDTO getInventory(Long productId) {
        log.info("Fetching inventory for productId: {}", productId);

        Inventory inventory = validateInventory(productId);

        return mapToResponse(inventory);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isInStock(Long productId, Integer quantity) {
        log.info("Checking Stock for productId: {}", productId);

        Inventory inventory = validateInventory(productId);

        return inventory.getQuantity() >= quantity;
    }

    @Override
    public void reduceStock(Long productId, Integer quantity) {
        log.info("Reducing stock for productId: {}", productId);

        Inventory inventory = repository.findByProductIdForUpdate(productId)
                .orElseThrow(() -> new InventoryNotFoundException(productId));

        if (inventory.getQuantity() < quantity) {
            throw new InsufficientStockException(productId);
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);

        repository.save(inventory);

        log.info("Stock reduced for productId: {}. Remaining: {}", productId, inventory.getQuantity());
    }

    // Helper Mathods

    private Inventory validateInventory(Long productId) {
        return repository.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException(productId));
    }

    private InventoryResponseDTO mapToResponse(Inventory inventory) {
        return InventoryResponseDTO.builder()
                .productId(inventory.getProductId())
                .quantity(inventory.getQuantity())
                .build();
    }
}
