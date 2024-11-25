package com.congthanh.inventoryservice.repository;

import com.congthanh.inventoryservice.model.entity.Inventory;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface InventoryCustomRepository {

    Optional<Inventory> findBySku(String sku);

}
