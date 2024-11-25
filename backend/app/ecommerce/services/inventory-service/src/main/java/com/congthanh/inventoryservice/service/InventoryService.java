package com.congthanh.inventoryservice.service;

import com.congthanh.inventoryservice.model.request.InventoryRequest;
import com.congthanh.inventoryservice.model.dto.InventoryDTO;

public interface InventoryService {

    InventoryDTO addInventoryItem(InventoryRequest request);

    InventoryDTO updateInventoryQuantity(String sku, Integer quantity);

    InventoryDTO getInventoryItem(String sku);

    void removeInventoryItem(String sku);

    boolean isInStock(String sku, Integer requiredQuantity);
}
