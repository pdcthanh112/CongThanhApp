package com.congthanh.productservice.repository.productView;

import com.congthanh.productservice.model.entity.ProductView;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProductViewRepository extends JpaRepository<ProductView, Long>, ProductViewCustomRepository {

}
