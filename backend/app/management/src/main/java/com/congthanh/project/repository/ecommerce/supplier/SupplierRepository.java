package com.congthanh.project.repository.ecommerce.supplier;

import com.congthanh.project.entity.ecommerce.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String>, SupplierCustomRepository {

    Optional<Supplier> findById(String id);

}
