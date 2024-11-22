package com.congthanh.project.repository;

import com.congthanh.project.model.entity.Supplier;
import com.congthanh.project.model.response.ProductResponse;
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
