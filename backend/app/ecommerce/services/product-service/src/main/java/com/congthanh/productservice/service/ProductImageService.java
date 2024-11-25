package com.congthanh.productservice.service;

import com.congthanh.productservice.model.dto.ProductImageDTO;

import java.util.List;

public interface ProductImageService {

    List<ProductImageDTO> getImageByProduct(String productId);

    ProductImageDTO getDefaultImageByProduct(String productId);

    ProductImageDTO addProductImage(ProductImageDTO productImageDTO);

}
