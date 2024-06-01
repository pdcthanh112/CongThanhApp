package com.congthanh.project.repository.ecommerce.productImage;

import com.congthanh.project.entity.ecommerce.ProductImage;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ProductImageCustomRepository {

    ProductImage getDefaultImageByProduct(String productId);

}
