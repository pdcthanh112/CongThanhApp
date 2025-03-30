package com.congthanh.productservice.model.viewmodel;

import com.congthanh.productservice.model.entity.VariantOptionValue;

public record ProductOptionValueGetVm(Long id,
                                      Long productOptionId,
                                      String productOptionValue,
                                      String productOptionName,
                                      int displayOrder) {
    public static ProductOptionValueGetVm fromModel(VariantOptionValue productOptionValue) {
        return new ProductOptionValueGetVm(
                productOptionValue.getId(),
                productOptionValue.getVariantOption().getId(),
                productOptionValue.getValue(),
                productOptionValue.getVariantOption().getName(),
                productOptionValue.getDisplayOrder()
        );
    }
}
