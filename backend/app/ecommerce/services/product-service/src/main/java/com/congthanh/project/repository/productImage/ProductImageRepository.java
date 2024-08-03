package com.congthanh.project.repository.productImage;

import com.congthanh.project.entity.ProductImage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProductImageRepository extends JpaRepository<ProductImage, Long>, ProductImageCustomRepository {
}
