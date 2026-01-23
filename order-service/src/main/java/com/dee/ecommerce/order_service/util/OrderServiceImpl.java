package com.dee.ecommerce.order_service.util;

import com.dee.ecommerce.order_service.dto.OrderRequest;
import com.dee.ecommerce.order_service.dto.OrderResponse;
import com.dee.ecommerce.order_service.entity.Order;
import com.dee.ecommerce.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.nio.ReadOnlyBufferException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService {
    @Override
    public OrderResponse createOrder(OrderRequest request) {
        log.info("Initiating order request for User: {}", request.getUserId());

        Order order = new Order();
        order.set
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {
        return null;
    }
}
