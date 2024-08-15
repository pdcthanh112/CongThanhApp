package com.congthanh.project.service;

import com.congthanh.project.dto.InventoryRequest;
import com.congthanh.project.dto.InventoryResponse;
import com.congthanh.project.entity.Inventory;

public interface InventoryService {

    Inventory addInventoryItem(InventoryRequest request);

    Inventory updateInventoryQuantity(String sku, Integer quantity);

    InventoryResponse getInventoryItem(String sku);

    void removeInventoryItem(String sku);

    boolean isInStock(String sku, Integer requiredQuantity);
}
