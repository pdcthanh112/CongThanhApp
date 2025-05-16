package com.congthanh.productservice.repository.variantOptionCombination;

import com.congthanh.productservice.model.entity.Product;
import com.congthanh.productservice.model.entity.VariantOptionCombination;

import java.util.List;

public interface VariantOptionCombinationCustomRepository {

    List<VariantOptionCombination> findAllByProduct(Product product);
}
