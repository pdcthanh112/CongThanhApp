package com.congthanh.productservice.model.viewmodel;

import java.math.BigDecimal;
import java.util.List;

public record ProductDetailVm(
        String id,
        String name,
        String brandName,
        List<String> category,
        List<ProductAttributeVm> attribute,
        String description,
        Boolean isFeatured,
        Boolean hasVariant,
        BigDecimal price,
        String thumbnail,
        List<String> images
) {
}
