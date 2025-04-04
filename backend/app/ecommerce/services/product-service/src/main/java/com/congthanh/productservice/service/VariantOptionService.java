package com.congthanh.productservice.service;

import com.congthanh.productservice.model.viewmodel.ProductOptionValueGetVm;

import java.util.List;

public interface VariantOptionService {

    List<ProductOptionValueGetVm> getVariantOptionValueByProduct(String productId);
}
