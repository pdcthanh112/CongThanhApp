package com.congthanh.productservice.repository.productVariant;

import com.congthanh.productservice.model.entity.ProductVariant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProductVariantRepository extends JpaRepository<ProductVariant, String>, ProductVariantCustomRepository {

}
