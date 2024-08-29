package com.congthanh.project.service;

import com.congthanh.project.dto.PromotionDTO;

public interface PromotionService {

    PromotionDTO getVoucherByCode(String code);

    PromotionDTO createVoucher(PromotionDTO promotionDTO);

    PromotionDTO updateVoucher(String voucherId, PromotionDTO promotionDTO);

    boolean checkValidVoucher(String code);
}
