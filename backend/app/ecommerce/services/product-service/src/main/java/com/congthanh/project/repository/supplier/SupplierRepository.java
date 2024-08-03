package com.congthanh.project.repository.supplier;

import com.congthanh.project.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String>, SupplierCustomRepository {

    Optional<Supplier> findById(String id);

}
