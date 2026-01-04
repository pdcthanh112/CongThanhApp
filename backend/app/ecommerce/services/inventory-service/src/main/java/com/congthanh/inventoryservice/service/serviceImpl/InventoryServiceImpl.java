package com.congthanh.inventoryservice.service.serviceImpl;

import com.congthanh.inventoryservice.model.mapper.InventoryMapper;
import com.congthanh.inventoryservice.model.request.InventoryRequest;
import com.congthanh.inventoryservice.model.dto.InventoryDTO;
import com.congthanh.inventoryservice.model.entity.Inventory;
import com.congthanh.inventoryservice.exception.ecommerce.NotFoundException;
import com.congthanh.inventoryservice.repository.InventoryRepository;
import com.congthanh.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    private KafkaTemplate<String, Object> kafkaTemplate;

    private static final String INVENTORY_UPDATED_TOPIC = "inventory-updated";
    private static final String INVENTORY_FAILED_TOPIC = "inventory-failed";

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

    @KafkaListener(topics = "payment-processed")
    @Transactional
    public void updateInventory(InventoryRequestEvent event) {
        try {
            log.info("Updating inventory for order: {}", event.getOrderId());

            boolean allItemsAvailable = true;
            List<String> unavailableItems = new ArrayList<>();
            List<InventoryChange> changes = new ArrayList<>();

            // Kiểm tra và cập nhật inventory cho từng sản phẩm
            for (OrderItem item : event.getItems()) {
                Inventory inventory = inventoryRepository.findByProductId(item.getProductId())
                        .orElseThrow(() -> new ProductNotFoundException("Product not found: " + item.getProductId()));

                if (inventory.getQuantity() < item.getQuantity()) {
                    allItemsAvailable = false;
                    unavailableItems.add(item.getProductName() + " (requested: " + item.getQuantity() +
                            ", available: " + inventory.getQuantity() + ")");
                } else {
                    // Lưu thay đổi để thực hiện sau khi kiểm tra tất cả items
                    changes.add(new InventoryChange(inventory, item.getQuantity()));
                }
            }

            if (allItemsAvailable) {
                // Thực hiện cập nhật số lượng
                for (InventoryChange change : changes) {
                    Inventory inventory = change.getInventory();
                    int newQuantity = inventory.getQuantity() - change.getReduceBy();
                    inventory.setQuantity(newQuantity);
                    inventoryRepository.save(inventory);

                    // Lưu lịch sử
                    InventoryHistory history = new InventoryHistory();
                    history.setProductId(inventory.getProductId());
                    history.setOrderId(event.getOrderId());
                    history.setQuantityChanged(-change.getReduceBy());
                    history.setTimestamp(new Date());
                    history.setOperation(InventoryOperation.RESERVE);
                    historyRepository.save(history);
                }

                // Gửi sự kiện cập nhật inventory thành công
                InventoryUpdatedEvent updatedEvent = new InventoryUpdatedEvent();
                updatedEvent.setSagaId(event.getSagaId());
                updatedEvent.setOrderId(event.getOrderId());
                updatedEvent.setOrderDTO(event.getOrderDTO());

                kafkaTemplate.send(INVENTORY_UPDATED_TOPIC, updatedEvent);

                log.info("Inventory updated successfully for order: {}", event.getOrderId());
            } else {
                // Gửi sự kiện cập nhật inventory thất bại
                InventoryFailedEvent failedEvent = new InventoryFailedEvent();
                failedEvent.setSagaId(event.getSagaId());
                failedEvent.setOrderId(event.getOrderId());
                failedEvent.setOrderDTO(event.getOrderDTO());
                failedEvent.setReason("Some items are out of stock: " + String.join(", ", unavailableItems));

                kafkaTemplate.send(INVENTORY_FAILED_TOPIC, failedEvent);

                log.warn("Inventory update failed for order {}: some items out of stock", event.getOrderId());
            }
        } catch (Exception e) {
            log.error("Error updating inventory for order {}: {}", event.getOrderId(), e.getMessage());

            // Gửi sự kiện cập nhật inventory thất bại
            InventoryFailedEvent failedEvent = new InventoryFailedEvent();
            failedEvent.setSagaId(event.getSagaId());
            failedEvent.setOrderId(event.getOrderId());
            failedEvent.setOrderDTO(event.getOrderDTO());
            failedEvent.setReason("System error: " + e.getMessage());

            kafkaTemplate.send(INVENTORY_FAILED_TOPIC, failedEvent);
        }
    }

    @KafkaListener(topics = "delivery-failed")
    @Transactional
    public void rollbackInventory(InventoryRollbackEvent event) {
        try {
            log.info("Rolling back inventory for order: {}", event.getOrderId());

            for (OrderItem item : event.getItems()) {
                Inventory inventory = inventoryRepository.findByProductId(item.getProductId())
                        .orElseThrow(() -> new ProductNotFoundException("Product not found: " + item.getProductId()));

                // Khôi phục số lượng
                inventory.setQuantity(inventory.getQuantity() + item.getQuantity());
                inventoryRepository.save(inventory);

                // Lưu lịch sử
                InventoryHistory history = new InventoryHistory();
                history.setProductId(inventory.getProductId());
                history.setOrderId(event.getOrderId());
                history.setQuantityChanged(item.getQuantity());
                history.setTimestamp(new Date());
                history.setOperation(InventoryOperation.ROLLBACK);
                historyRepository.save(history);
            }

            log.info("Inventory rolled back successfully for order: {}", event.getOrderId());
        } catch (Exception e) {
            log.error("Error rolling back inventory for order {}: {}", event.getOrderId(), e.getMessage());
        }
    }

    private record InventoryChange( Inventory inventory,  int reduceBy) { }

}


