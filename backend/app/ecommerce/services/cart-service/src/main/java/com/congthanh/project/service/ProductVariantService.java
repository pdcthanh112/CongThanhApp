package com.congthanh.project.service;

import com.congthanh.project.dto.ProductVariantDTO;

import java.util.List;

public interface ProductVariantService {

    List<ProductVariantDTO> getProductVariantByProductId(String productId);
}
