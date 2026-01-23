package com.dee.ecommerce.order_service.service;

import com.dee.ecommerce.order_service.dto.OrderRequest;
import com.dee.ecommerce.order_service.dto.OrderResponse;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    OrderResponse getOrderById(Long id);
}
