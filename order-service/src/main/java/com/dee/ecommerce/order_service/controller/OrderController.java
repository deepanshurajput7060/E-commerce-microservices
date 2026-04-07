package com.dee.ecommerce.order_service.controller;


import com.dee.ecommerce.order_service.dto.OrderItemRequest;
import com.dee.ecommerce.order_service.dto.OrderRequest;
import com.dee.ecommerce.order_service.dto.OrderResponse;
import com.dee.ecommerce.order_service.service.OrderService;
import com.dee.ecommerce.order_service.util.SecurityUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Validated // 1. REQUIRED for @PathVariable and @RequestParam validation to work
public class OrderController {

    private final OrderService orderService;

    // Create Order
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        // Added @Valid to trigger validation inside the DTO
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    // Get Order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable @Min(value = 1, message = "Order ID must be at least 1") Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    // Get Orders by User
    @GetMapping("/user")
    public ResponseEntity<Page<OrderResponse>> getOrdersByUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        String userId = SecurityUtil.getCurrentUsedId();

        return ResponseEntity.ok(
                orderService.getOrdersByUser(userId, page, size, sortBy, direction)
        );
    }

    // Cancel Order
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(
            @PathVariable @Min(1) Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Order cancelled successfully");
    }

    // Update Status (Admin)
    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable @Min(1) Long orderId,
            @RequestParam @Pattern(regexp = "CREATED|CONFIRMED|PAID|SHIPPED|DELIVERED|CANCELLED",
                    message = "Invalid status value") String status) {
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok("Order status updated");
    }
}
