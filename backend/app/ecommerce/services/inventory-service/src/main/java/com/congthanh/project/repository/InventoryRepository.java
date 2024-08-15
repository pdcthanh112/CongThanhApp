package com.congthanh.project.repository;

import com.congthanh.project.entity.Inventory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface InventoryRepository extends JpaRepository<Inventory, Long>, InventoryCustomRepository {

}
