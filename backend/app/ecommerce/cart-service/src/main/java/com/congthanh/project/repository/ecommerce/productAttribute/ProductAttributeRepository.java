package com.congthanh.project.repository.ecommerce.productAttribute;

import com.congthanh.project.entity.ecommerce.ProductAttribute;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long>, ProductAttributeCustomRepository {
}
