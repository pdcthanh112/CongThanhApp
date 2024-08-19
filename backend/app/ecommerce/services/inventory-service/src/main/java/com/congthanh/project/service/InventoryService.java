package com.congthanh.project.service;

import com.congthanh.project.model.request.InventoryRequest;
import com.congthanh.project.dto.InventoryDTO;
import com.congthanh.project.entity.Inventory;

public interface InventoryService {

    InventoryDTO addInventoryItem(InventoryRequest request);

    InventoryDTO updateInventoryQuantity(String sku, Integer quantity);

    InventoryDTO getInventoryItem(String sku);

    void removeInventoryItem(String sku);

    boolean isInStock(String sku, Integer requiredQuantity);
}
