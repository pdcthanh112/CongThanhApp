package com.congthanh.project.repository.supplier;

import com.congthanh.project.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface SupplierCustomRepository {

    Supplier getSupplierBySlug(String slug);

    Page<Product> getProductFromSupplier(String storeId, Pageable pageable);
}
