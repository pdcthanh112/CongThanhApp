package com.congthanh.project.repository;

import com.congthanh.project.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String>, SupplierCustomRepository {

}
