package com.congthanh.productservice.repository.productImage;

import com.congthanh.productservice.model.entity.ProductImage;

import java.util.List;

public interface ProductImageCustomRepository {

    List<ProductImage> getProductImages(String productId);

    void deleteByProductId(String productId);

}
