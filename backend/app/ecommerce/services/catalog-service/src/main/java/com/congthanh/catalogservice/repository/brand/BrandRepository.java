package com.congthanh.catalogservice.repository.brand;

import com.congthanh.catalogservice.model.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BrandRepository extends JpaRepository<Brand, Long>, BrandCustomRepository {
}
