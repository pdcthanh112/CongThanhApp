package com.congthanh.productservice.repository.productAttribute;

import com.congthanh.productservice.model.entity.attribute.ProductAttribute;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long>, ProductAttributeCustomRepository {
}
