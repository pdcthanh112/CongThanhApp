package com.congthanh.productservice.repository.productImage;

import java.util.List;

public interface ProductImageCustomRepository {

    List<ProductImage> getProductImages(String productId);

    void deleteByProductId(String productId);

}
