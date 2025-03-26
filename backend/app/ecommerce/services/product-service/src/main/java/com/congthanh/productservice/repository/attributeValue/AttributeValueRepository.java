package com.congthanh.productservice.repository.attributeValue;

import com.congthanh.productservice.model.entity.ProductAttributeValue;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface AttributeValueRepository extends JpaRepository<ProductAttributeValue, Long>, AttributeValueCustomRepository {
}
