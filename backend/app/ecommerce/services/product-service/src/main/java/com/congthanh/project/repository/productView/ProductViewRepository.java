package com.congthanh.project.repository.productView;

import com.congthanh.project.entity.ProductView;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProductViewRepository extends JpaRepository<ProductView, Long>, ProductViewCustomRepository {

}
