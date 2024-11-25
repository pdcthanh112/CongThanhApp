package com.congthanh.productservice.repository.productView;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProductViewCustomRepository {

    Long getTotalViewOfProduct(String productId);

}
