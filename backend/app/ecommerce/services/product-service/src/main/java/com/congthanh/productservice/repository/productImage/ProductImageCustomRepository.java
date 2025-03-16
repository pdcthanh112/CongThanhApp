package com.congthanh.productservice.repository.productImage;

import com.congthanh.productservice.model.entity.ProductImage;

public interface ProductImageCustomRepository {

    ProductImage getDefaultImageByProduct(String productId);

    void deleteByProductId(String productId);

}
