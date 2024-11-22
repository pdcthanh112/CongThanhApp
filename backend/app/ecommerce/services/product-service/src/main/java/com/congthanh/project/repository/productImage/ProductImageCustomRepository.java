package com.congthanh.project.repository.productImage;

import com.congthanh.project.model.entity.ProductImage;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProductImageCustomRepository {

    ProductImage getDefaultImageByProduct(String productId);

}
