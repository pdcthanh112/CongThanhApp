package com.congthanh.project.serviceImpl;

import com.congthanh.project.dto.InventoryDTO;
import com.congthanh.project.dto.InventoryRequest;
import com.congthanh.project.dto.InventoryResponse;
import com.congthanh.project.entity.Inventory;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl {

    private final InventoryRepository inventoryRepository;

    private final WebClient webClient;

    @KafkaListener(topics = "product-events")
    public void handleProductEvent(ProductEvent event) {
        if ("CREATE".equals(event.getEventType())) {
            createInventoryForProduct(event.getProduct());
        }
    }

    public Inventory addInventoryItem(InventoryRequest request) {
        Inventory item = Inventory.builder()
                .sku(request.getSku())
                .quantity(request.getQuantity())
                .build();

        return inventoryRepository.save(item);
    }

    public Inventory updateInventoryQuantity(String sku, Integer quantity) {
        Inventory item = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new NotFoundException("Inventory item not found for SKU: " + sku));
        item.setQuantity(quantity);
        return inventoryRepository.save(item);
    }

    public InventoryResponse getInventoryItem(String sku) {
        Inventory item = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new NotFoundException("Inventory item not found for SKU: " + sku));
        return new InventoryResponse(item.getSku(), item.getQuantity(), "ACTIVE");
    }

    public void removeInventoryItem(String sku) {
        Inventory item = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new NotFoundException("Inventory item not found for SKU: " + sku));
        inventoryRepository.delete(item);
    }

    public boolean isInStock(String sku, Integer requiredQuantity) {
        return inventoryRepository.findBySku(sku)
                .map(item -> item.getQuantity() >= requiredQuantity)
                .orElse(false);
    }

}
