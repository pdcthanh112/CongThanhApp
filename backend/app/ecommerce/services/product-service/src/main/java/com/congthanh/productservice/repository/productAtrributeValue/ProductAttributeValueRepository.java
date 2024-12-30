package com.congthanh.productservice.repository.productAtrributeValue;

import com.congthanh.productservice.model.entity.attribute.ProductAttributeValue;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProductAttributeValueRepository extends JpaRepository<ProductAttributeValue, Long>, ProductAttributeValueCustomRepository {
}
