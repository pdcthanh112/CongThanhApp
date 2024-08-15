package com.congthanh.project.repository;

import com.congthanh.project.entity.Inventory;

import java.util.Optional;

public class InventoryCustomRepositoryImpl implements InventoryCustomRepository{
    @Override
    public Optional<Inventory> findBySku(String sku) {
        return Optional.empty();
    }
}
