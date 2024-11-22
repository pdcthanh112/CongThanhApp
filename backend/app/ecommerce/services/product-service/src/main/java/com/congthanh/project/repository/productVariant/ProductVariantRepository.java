package com.congthanh.project.repository.productVariant;

import com.congthanh.project.model.entity.ProductVariant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProductVariantRepository extends JpaRepository<ProductVariant, String>, ProductVariantCustomRepository {

}
