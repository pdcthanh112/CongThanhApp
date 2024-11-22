package com.congthanh.project.service.serviceImpl;

import com.congthanh.project.model.mapper.InventoryMapper;
import com.congthanh.project.model.request.InventoryRequest;
import com.congthanh.project.model.dto.InventoryDTO;
import com.congthanh.project.model.entity.Inventory;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.repository.InventoryRepository;
import com.congthanh.project.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryDTO addInventoryItem(InventoryRequest request) {
        ProductResponse product = null;
        assert product != null;
        Inventory item = Inventory.builder()
                .sku(product.getSku())
                .stock(request.getStock())
                .build();
        Inventory savedInventory = inventoryRepository.save(item);
        return InventoryMapper.mapCartEntityToDTO(savedInventory);
    }

    public InventoryDTO updateInventoryQuantity(String sku, Integer quantity) {
        Inventory item = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new NotFoundException("Inventory item not found for SKU: " + sku));
        item.setStock(quantity);
        Inventory savedInventory = inventoryRepository.save(item);
        return InventoryMapper.mapCartEntityToDTO(savedInventory);
    }

    public InventoryDTO getInventoryItem(String sku) {
        Inventory item = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new NotFoundException("Inventory item not found for SKU: " + sku));
        return new InventoryDTO(item.getId(), item.getSku(), item.getStock());
    }

    public void removeInventoryItem(String sku) {
        Inventory item = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new NotFoundException("Inventory item not found for SKU: " + sku));
        inventoryRepository.delete(item);
    }

    public boolean isInStock(String sku, Integer requiredQuantity) {
        return inventoryRepository.findBySku(sku)
                .map(item -> item.getStock() >= requiredQuantity)
                .orElse(false);
    }

//    @KafkaListener(topics = "product-topic")
//    private void handleCreateProduct(ProductEvent productEvent) {
//        if ("PRODUCT_CREATED".equals(productEvent.eventType())) {
//            ProductResponse product = productEvent.product();
//            InventoryRequest inventory = InventoryRequest.builder().quantity(0).sku(product.getSku()).build();
//            this.addInventoryItem(inventory);
//        }
//    }

    @KafkaListener(topics = "create-product-variant-topic")
    private void handleCreateVariant(ProductVariantResponse variant) {
            InventoryRequest inventory = InventoryRequest.builder().stock(0).sku(variant.getSku()).build();
            this.addInventoryItem(inventory);

    }

}
