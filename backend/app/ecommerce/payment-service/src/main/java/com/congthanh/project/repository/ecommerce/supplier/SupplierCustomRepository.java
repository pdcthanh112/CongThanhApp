package com.congthanh.project.repository.ecommerce.supplier;

import com.congthanh.project.entity.ecommerce.Product;
import com.congthanh.project.entity.ecommerce.Supplier;
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
