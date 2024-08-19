package com.congthanh.project.serviceImpl;

import com.congthanh.project.model.mapper.InventoryMapper;
import com.congthanh.project.model.request.InventoryRequest;
import com.congthanh.project.dto.InventoryDTO;
import com.congthanh.project.dto.ProductVariantResponse;
import com.congthanh.project.entity.Inventory;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.dto.ProductResponse;
import com.congthanh.project.repository.InventoryRepository;
import com.congthanh.project.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    private final WebClient webClient;

    record ProductEvent (String eventType, ProductResponse product) { }
    record ProductVariantEvent (String eventType, ProductVariantResponse variant) { }

    public InventoryDTO addInventoryItem(InventoryRequest request) {
        ProductResponse product = webClient.get().uri("/product/" + request.getSku()).retrieve().bodyToMono(ProductResponse.class).block();
        assert product != null;
        Inventory item = Inventory.builder()
                .sku(product.getSku())
                .quantity(request.getQuantity())
                .build();
        Inventory savedInventory = inventoryRepository.save(item);
        return InventoryMapper.mapCartEntityToDTO(savedInventory);
    }

    public InventoryDTO updateInventoryQuantity(String sku, Integer quantity) {
        Inventory item = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new NotFoundException("Inventory item not found for SKU: " + sku));
        item.setQuantity(quantity);
        Inventory savedInventory = inventoryRepository.save(item);
        return InventoryMapper.mapCartEntityToDTO(savedInventory);
    }

    public InventoryDTO getInventoryItem(String sku) {
        Inventory item = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new NotFoundException("Inventory item not found for SKU: " + sku));
        return new InventoryDTO(item.getId(), item.getSku(), item.getQuantity());
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

    @KafkaListener(topics = "product-topic")
    private void handleCreateProduct(ProductEvent productEvent) {
        if ("PRODUCT_CREATED".equals(productEvent.eventType())) {
            ProductResponse product = productEvent.product();
            InventoryRequest inventory = InventoryRequest.builder().quantity(0).sku(product.getSku()).build();
            this.addInventoryItem(inventory);
        }
    }

    @KafkaListener(topics = "product-variant-topic")
    private void handleCreateVariant(ProductVariantEvent variantEvent) {
        if ("VARIANT_CREATED".equals(variantEvent.eventType())) {
            ProductVariantResponse variant = variantEvent.variant();
            Inventory inventory = Inventory.builder().quantity(0).sku(variant.getId()).build();
            inventoryRepository.save(inventory);
        }
    }

}
