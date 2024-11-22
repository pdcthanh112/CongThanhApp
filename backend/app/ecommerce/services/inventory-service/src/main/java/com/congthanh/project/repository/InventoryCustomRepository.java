package com.congthanh.project.repository;

import com.congthanh.project.model.entity.Inventory;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface InventoryCustomRepository {

    Optional<Inventory> findBySku(String sku);

}
