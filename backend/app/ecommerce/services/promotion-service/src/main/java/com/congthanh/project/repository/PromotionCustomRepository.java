package com.congthanh.project.repository;

import com.congthanh.project.model.entity.Promotion;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PromotionCustomRepository {

    Promotion getPromotionByCode(String code);

    Long countUsedPromotion(String promotionId);

}
