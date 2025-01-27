package com.congthanh.productservice.repository.productAtrributeValue;

import com.congthanh.productservice.model.entity.attribute.ProductAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAttributeValueRepository extends JpaRepository<ProductAttributeValue, Long>, ProductAttributeValueCustomRepository {
}
