package com.congthanh.productservice.service;

import com.congthanh.productservice.model.viewmodel.ProductOptionCombinationGetVm;
import com.congthanh.productservice.model.viewmodel.ProductOptionValueGetVm;

import java.util.List;

public interface VariantOptionService {

    List<ProductOptionValueGetVm> getVariantOptionValueByProduct(String productId);

    List<ProductOptionCombinationGetVm> getVariantOptionCombinationByProduct(String productId);
}
