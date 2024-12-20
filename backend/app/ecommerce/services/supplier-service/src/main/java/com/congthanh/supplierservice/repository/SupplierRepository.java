package com.congthanh.supplierservice.repository;

import com.congthanh.supplierservice.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String>, SupplierCustomRepository {

}
