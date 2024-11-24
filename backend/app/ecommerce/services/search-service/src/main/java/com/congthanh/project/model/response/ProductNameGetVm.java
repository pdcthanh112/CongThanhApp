package com.congthanh.project.model.response;

import com.congthanh.project.model.document.Product;

public record ProductNameGetVm(String name) {
    public static ProductNameGetVm fromModel(Product product) {
        return new ProductNameGetVm(
                product.getName()
        );
    }
}
