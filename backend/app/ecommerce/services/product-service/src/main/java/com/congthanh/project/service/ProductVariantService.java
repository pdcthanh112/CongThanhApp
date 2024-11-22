package com.congthanh.project.service;

import com.congthanh.project.model.dto.ProductVariantDTO;
import com.congthanh.project.model.request.ProductVariantRequest;

import java.util.List;

public interface ProductVariantService {

    List<ProductVariantDTO> getProductVariantByProductId(String productId);

    ProductVariantDTO createProductVariant(ProductVariantRequest requestDTO);
}
