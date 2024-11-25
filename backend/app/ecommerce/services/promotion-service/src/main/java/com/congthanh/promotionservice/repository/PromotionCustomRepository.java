package com.congthanh.promotionservice.repository;

import com.congthanh.promotionservice.model.entity.Promotion;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PromotionCustomRepository {

    Promotion getPromotionByCode(String code);

    Long countUsedPromotion(String promotionId);

}
