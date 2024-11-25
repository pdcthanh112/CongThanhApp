package com.congthanh.promotionservice.repository;

import com.congthanh.promotionservice.model.entity.Promotion;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PromotionRepository extends JpaRepository<Promotion, Long>, PromotionCustomRepository {
}
