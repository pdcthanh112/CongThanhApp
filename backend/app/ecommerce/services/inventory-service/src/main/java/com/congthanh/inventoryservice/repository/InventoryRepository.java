package com.congthanh.inventoryservice.repository;

import com.congthanh.inventoryservice.model.entity.Inventory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface InventoryRepository extends JpaRepository<Inventory, String>, InventoryCustomRepository {

}
