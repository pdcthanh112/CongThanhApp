package com.congthanh.project.repository.productView;

import com.congthanh.project.entity.ecommerce.ProductView;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProductViewCustomRepository {

    Long getTotalViewOfProduct(String productId);

}
