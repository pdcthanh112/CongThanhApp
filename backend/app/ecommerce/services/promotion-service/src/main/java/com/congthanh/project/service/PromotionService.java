package com.congthanh.project.service;

import com.congthanh.project.dto.PromotionDTO;

public interface PromotionService {

    PromotionDTO getPromotionByCode(String code);

    PromotionDTO createPromotion(PromotionDTO promotionDTO);

    PromotionDTO updatePromotion(Long promotionId, PromotionDTO promotionDTO);

    boolean checkValidPromotion(String code);
}
