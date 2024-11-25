package com.congthanh.promotionservice.service;

import com.congthanh.promotionservice.model.dto.PromotionDTO;

public interface PromotionService {

    PromotionDTO getPromotionByCode(String code);

    PromotionDTO createPromotion(PromotionDTO promotionDTO);

    PromotionDTO updatePromotion(Long promotionId, PromotionDTO promotionDTO);

    boolean checkValidPromotion(String code);
}
