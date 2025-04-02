package com.congthanh.productservice.repository.variantOptionValue;

import com.congthanh.productservice.model.entity.Product;
import com.congthanh.productservice.model.entity.VariantOptionValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariantOptionValueRepository extends JpaRepository<VariantOptionValue, Long> {

    List<VariantOptionValue> findAllByProduct(Product product);
}
