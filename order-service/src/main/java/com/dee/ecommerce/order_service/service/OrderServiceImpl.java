package com.dee.ecommerce.order_service.service;

import com.dee.ecommerce.order_service.client.ProductClient;
import com.dee.ecommerce.order_service.dto.OrderItemRequest;
import com.dee.ecommerce.order_service.dto.OrderRequest;
import com.dee.ecommerce.order_service.dto.OrderResponse;
import com.dee.ecommerce.order_service.dto.ProductResponse;
import com.dee.ecommerce.order_service.entity.Order;
import com.dee.ecommerce.order_service.entity.OrderItem;
import com.dee.ecommerce.order_service.exception.InvalidOrderException;
import com.dee.ecommerce.order_service.exception.ResourceNotFoundException;
import com.dee.ecommerce.order_service.repository.OrderRepository;
import com.dee.ecommerce.order_service.util.OrderStatus;
import com.dee.ecommerce.order_service.util.SecurityUtil;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final ProductClient productClient;

    // ================= CREATE ORDER =================

    @Override
    @Retry(name = "productService")
    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackCreateOrder")
    public OrderResponse createOrder(OrderRequest request) {

        Order order = new Order();
        order.setUserId(SecurityUtil.getCurrentUsedId()); // TEMP
        order.setStatus(OrderStatus.CREATED);

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest reqItem : request.getItems()) {

            Long productId = reqItem.getProductId();
            Integer quantity = reqItem.getQuantity();

            // Step 1: Fetch product
            ProductResponse product = productClient.getProductById(productId);

            // Step 2: Validate stock
            if (product.getQuantity() < quantity) {
                throw new InvalidOrderException("Insufficient stock for product: " + productId);
            }

            // Step 3: Reduce stock
            try {
                productClient.reduceStock(productId, quantity);
            } catch (Exception ex) {
                throw new RuntimeException("Failed to reduce stock. Try again.");
            }

            // Step 4: Create order item
            BigDecimal price = product.getPrice();

            OrderItem item = new OrderItem();
            item.setProductId(productId);
            item.setQuantity(quantity);
            item.setPriceAtPurchase(price);

            order.addItem(item);

            totalAmount = totalAmount.add(
                    price.multiply(BigDecimal.valueOf(quantity))
            );
        }

        order.setTotalAmount(totalAmount);

        Order savedOrder = repository.save(order);

        return mapToResponse(savedOrder);
    }

    // ================= GET ORDER BY ID =================
    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {
        log.info("Initiating Fetch request for Order with Id: {}", id);

        Order order = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        return mapToResponse(order);
    }

    // ================= GET ORDERS BY USER =================
    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> getOrdersByUser(String userId, int page, int size, String sortBy, String direction) {

        log.info("Initiating Fetch order request for User with Id: {}", userId);

        // Pagination safety
        page = Math.max(page, 0);
        size = Math.min(size, 50);

        // Sorting safety
        List<String> allowedSortFields = List.of("createdAt", "totalAmount", "status");
        if (!allowedSortFields.contains(sortBy)) {
            sortBy = "createdAt";
        }

        Sort.Direction dir = Sort.Direction.fromString(direction);
        Sort sort = Sort.by(dir, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Order> pageOrder = repository.findByUserId(userId, pageable);

        return pageOrder.map(this::mapToResponse);
    }

    // ================= CANCEL ORDER =================
    @Override
    public void cancelOrder(Long orderId) {
        log.info("Initiating Cancelling request for Order with Id: {}", orderId);

        Order order = repository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new InvalidOrderException("Only CREATED orders can be cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);
        // No need to call save() → JPA dirty checking
    }

    // ================= UPDATE ORDER STATUS =================
    @Override
    public void updateOrderStatus(Long orderId, String status) {
        log.info("Initiating Update Order Status request for Order with Id: {}", orderId);

        Order order = repository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new InvalidOrderException("Cancelled order cannot be updated.");
        }

        OrderStatus newStatus;
        try {
            newStatus = OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidOrderException("Invalid order status: " + status);
        }

        validateStatusTransition(order.getStatus(), newStatus);

        order.setStatus(newStatus);
    }

    // ================= HELPER METHODS =================

    public OrderResponse fallbackCreateOrder(OrderRequest request, Throwable ex) {
        throw new RuntimeException("Product service is down. Please try again later.");
    }

    private OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }

    // Business rule for status transitions (future-ready)
    private void validateStatusTransition(OrderStatus current, OrderStatus newStatus) {

        if (current == OrderStatus.CANCELLED) {
            throw new InvalidOrderException("Cancelled order cannot be updated.");
        }

        // Example rules (extend later)
        if (current == OrderStatus.CREATED && newStatus == OrderStatus.SHIPPED) {
            throw new InvalidOrderException("Order must be PAID before SHIPPED.");
        }
    }
}
