package com.congthanh.searchservice.model.response;

import com.congthanh.searchservice.model.document.Product;

public record ProductNameGetVm(String name) {
    public static ProductNameGetVm fromModel(Product product) {
        return new ProductNameGetVm(
                product.getName()
        );
    }
}
