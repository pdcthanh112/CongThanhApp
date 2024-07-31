package com.congthanh.project.repository.ecommerce.attributeValue;

import com.congthanh.project.entity.ecommerce.ProductAttributeValue;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface AttributeValueRepository extends JpaRepository<ProductAttributeValue, Long>, AttributeValueCustomRepository {
}
