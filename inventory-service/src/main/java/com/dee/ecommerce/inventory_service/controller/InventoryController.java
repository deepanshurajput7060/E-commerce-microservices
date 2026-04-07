package com.dee.ecommerce.inventory_service.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dee.ecommerce.inventory_service.dto.InventoryRequestDTO;
import com.dee.ecommerce.inventory_service.dto.InventoryResponseDTO;
import com.dee.ecommerce.inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
@Validated  // Required for @Min/@NotNull on path/query params to trigger
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * Create or update stock for a product.
     * PUT is correct here — this is idempotent (same call = same result).
     */
    @PutMapping
    public ResponseEntity<InventoryResponseDTO> createOrUpdateStock(
            @Valid @RequestBody InventoryRequestDTO dto) {

        InventoryResponseDTO response = inventoryService.createOrUpdateStock(dto);
        return ResponseEntity.ok(response);
    }

    /**
     * Get current inventory for a product.
     */
    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponseDTO> getInventory(
            @PathVariable @NotNull @Min(1) Long productId) {

        return ResponseEntity.ok(inventoryService.getInventory(productId));
    }

    /**
     * Check whether a product has enough stock.
     * Query param: quantity — how much the caller needs.
     */
    @GetMapping("/{productId}/availability")
    public ResponseEntity<Boolean> isInStock(
            @PathVariable @NotNull @Min(1) Long productId,
            @RequestParam @Min(1) Integer quantity) {

        return ResponseEntity.ok(inventoryService.isInStock(productId, quantity));
    }

    /**
     * Reduce stock after a confirmed order.
     * PATCH is correct — partial update to quantity only.
     */
    @PatchMapping("/{productId}/reduce")
    public ResponseEntity<Void> reduceStock(
            @PathVariable @NotNull @Min(1) Long productId,
            @RequestParam @Min(1) Integer quantity) {

        inventoryService.reduceStock(productId, quantity);
        return ResponseEntity.noContent().build();  // 204 — action done, nothing to return
    }
}
