package com.congthanh.productservice.repository.productImage;

import com.congthanh.productservice.model.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long>, ProductImageCustomRepository {
}
