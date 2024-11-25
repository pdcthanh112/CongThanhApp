package com.congthanh.productservice.repository.productImage;

import com.congthanh.productservice.model.entity.ProductImage;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProductImageCustomRepository {

    ProductImage getDefaultImageByProduct(String productId);

}
