package com.dee.ecommerce.order_service.service;

import com.dee.ecommerce.order_service.dto.OrderRequest;
import com.dee.ecommerce.order_service.dto.OrderResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    OrderResponse getOrderById(Long id);

    Page<OrderResponse> getOrdersByUser(String userId, int page, int size, String sortBy, String direction);

    void cancelOrder(Long orderId);

    void updateOrderStatus(Long orderId, String status);

}
