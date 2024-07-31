package com.congthanh.project.service.ecommerce;

import com.congthanh.project.dto.ecommerce.ProductVariantDTO;

import java.util.List;

public interface ProductVariantService {

    List<ProductVariantDTO> getProductVariantByProductId(String productId);
}
