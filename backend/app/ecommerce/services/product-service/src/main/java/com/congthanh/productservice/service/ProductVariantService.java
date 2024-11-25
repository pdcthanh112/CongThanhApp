package com.congthanh.productservice.service;

import com.congthanh.productservice.model.dto.ProductVariantDTO;
import com.congthanh.productservice.model.request.ProductVariantRequest;

import java.util.List;

public interface ProductVariantService {

    List<ProductVariantDTO> getProductVariantByProductId(String productId);

    ProductVariantDTO createProductVariant(ProductVariantRequest requestDTO);
}
