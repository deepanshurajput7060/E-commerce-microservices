package com.dee.ecommerce.inventory_service.events;


import com.dee.ecommerce.inventory_service.dto.InventoryRequestDTO;
import com.dee.ecommerce.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductEventConsumer {

    private final InventoryService inventoryService;

    @KafkaListener(topics = "product-created-topic")
    public void handleProductCreated(ProductCreatedEvent event) {

        log.info("Received ProductCreatedEvent for productId={}", event.getProductId());

        inventoryService.createOrUpdateStock(
                new InventoryRequestDTO(event.getProductId(), 0));
    }
}
