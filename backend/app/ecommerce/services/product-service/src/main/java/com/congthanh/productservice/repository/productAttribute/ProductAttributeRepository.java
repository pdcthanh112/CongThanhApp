package com.congthanh.productservice.repository.productAttribute;

import com.congthanh.productservice.model.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long>, ProductAttributeCustomRepository {
}
