package com.congthanh.inventoryservice.repository;

import com.congthanh.inventoryservice.model.entity.Inventory;

import java.util.Optional;

public class InventoryCustomRepositoryImpl implements InventoryCustomRepository{
    @Override
    public Optional<Inventory> findBySku(String sku) {
        return Optional.empty();
    }
}
