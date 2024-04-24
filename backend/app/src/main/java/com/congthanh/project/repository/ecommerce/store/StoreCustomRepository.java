package com.congthanh.project.repository.ecommerce.store;

import com.congthanh.project.entity.ecommerce.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface StoreCustomRepository {

    Page<Product> getProductFromStore(String storeId, Pageable pageable);

}
