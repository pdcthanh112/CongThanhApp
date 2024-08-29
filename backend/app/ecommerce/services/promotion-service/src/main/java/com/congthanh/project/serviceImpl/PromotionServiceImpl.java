package com.congthanh.project.serviceImpl;

import com.congthanh.project.dto.PromotionDTO;
import com.congthanh.project.entity.Promotion;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.model.mapper.PromotionMapper;
import com.congthanh.project.repository.PromotionRepository;
import com.congthanh.project.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository voucherRepository;

    @Override
    public PromotionDTO getVoucherByCode(String code) {
        Promotion result = voucherRepository.getVoucherByCode(code);
        return result != null ? PromotionMapper.mapVoucherEntityToDTO(result) : null;
    }

    @Override
    public PromotionDTO createVoucher(PromotionDTO promotionDTO) {
        Promotion existPromotion = voucherRepository.getVoucherByCode(promotionDTO.getCode());
        if (existPromotion != null) throw new RuntimeException("Code exits");
        Promotion promotion = Promotion.builder()
                .code(promotionDTO.getCode())
                .type(promotionDTO.getType())
                .value(promotionDTO.getValue())
                .usageLimit(promotionDTO.getUsageLimit())
                .description(promotionDTO.getDescription())
                .startDate(promotionDTO.getStartDate())
                .endDate(promotionDTO.getEndDate())
                .status("NEW")
                .build();
        Promotion result = voucherRepository.save(promotion);
        return PromotionMapper.mapVoucherEntityToDTO(result);
    }

    @Override
    public PromotionDTO updateVoucher(String voucherId, PromotionDTO promotionDTO) {
        Promotion promotion = voucherRepository.findById(voucherId).orElseThrow(() -> new NotFoundException("voucher not found"));

        promotion.setCode(promotionDTO.getCode());
        promotion.setType(promotionDTO.getType());
        promotion.setValue(promotionDTO.getValue());
        promotion.setUsageLimit(promotionDTO.getUsageLimit());
        promotion.setDescription(promotionDTO.getDescription());
        promotion.setStartDate(promotionDTO.getStartDate());
        promotion.setEndDate(promotionDTO.getEndDate());

        Promotion result = voucherRepository.save(promotion);
        return PromotionMapper.mapVoucherEntityToDTO(result);
    }

    @Override
    public boolean checkValidVoucher(String code) {
        Promotion promotion = voucherRepository.getVoucherByCode(code);

        LocalDateTime currentDate = LocalDateTime.now();
        if (currentDate.isBefore(promotion.getStartDate()) || currentDate.isAfter(promotion.getEndDate()) || promotion.getStatus().equals("INACTIVE"))
            return false;
        if (promotion.getUsageLimit() == 0) return false;
        return true;
    }
}
