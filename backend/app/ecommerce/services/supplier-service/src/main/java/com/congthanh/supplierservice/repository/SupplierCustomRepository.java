package com.congthanh.supplierservice.repository;

import com.congthanh.supplierservice.model.entity.Supplier;
import com.congthanh.supplierservice.model.response.ProductResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface SupplierCustomRepository {

    Supplier getSupplierBySlug(String slug);

    Page<ProductResponse> getProductFromSupplier(String storeId, Pageable pageable);
}
