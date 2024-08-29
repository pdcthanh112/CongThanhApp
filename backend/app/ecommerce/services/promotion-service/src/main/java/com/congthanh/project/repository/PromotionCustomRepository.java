package com.congthanh.project.repository;

import com.congthanh.project.entity.Promotion;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PromotionCustomRepository {

    Promotion getVoucherByCode(String code);

    Long countUsedVoucher(String voucherId);

}
