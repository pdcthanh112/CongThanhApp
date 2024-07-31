package com.congthanh.project.service;

import com.congthanh.project.dto.ecommerce.ProductImageDTO;

import java.util.List;

public interface ProductImageService {

    List<ProductImageDTO> getImageByProduct(String productId);

    ProductImageDTO getDefaultImageByProduct(String productId);

    ProductImageDTO addProductImage(ProductImageDTO productImageDTO);

}
