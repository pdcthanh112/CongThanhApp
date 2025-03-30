package com.congthanh.productservice.repository.variantOptionValue;

import com.congthanh.productservice.model.entity.Product;
import com.congthanh.productservice.model.entity.VariantOptionValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VariantOptionValueRepository extends JpaRepository<VariantOptionValue, Long> {

    List<VariantOptionValue> findAllByProduct(Product product);
}
