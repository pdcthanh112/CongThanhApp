package com.congthanh.project.service.serviceImpl;

import com.congthanh.project.model.dto.PromotionDTO;
import com.congthanh.project.model.entity.Promotion;
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

    private final PromotionRepository promotionRepository;

    @Override
    public PromotionDTO getPromotionByCode(String code) {
        Promotion result = promotionRepository.getPromotionByCode(code);
        return result != null ? PromotionMapper.mapPromotionEntityToDTO(result) : null;
    }

    @Override
    public PromotionDTO createPromotion(PromotionDTO promotionDTO) {
        Promotion existPromotion = promotionRepository.getPromotionByCode(promotionDTO.getCode());
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
        Promotion result = promotionRepository.save(promotion);
        return PromotionMapper.mapPromotionEntityToDTO(result);
    }

    @Override
    public PromotionDTO updatePromotion(Long promotionId, PromotionDTO promotionDTO) {
        Promotion promotion = promotionRepository.findById(promotionId).orElseThrow(() -> new NotFoundException("Promotion not found"));

        promotion.setCode(promotionDTO.getCode());
        promotion.setType(promotionDTO.getType());
        promotion.setValue(promotionDTO.getValue());
        promotion.setUsageLimit(promotionDTO.getUsageLimit());
        promotion.setDescription(promotionDTO.getDescription());
        promotion.setStartDate(promotionDTO.getStartDate());
        promotion.setEndDate(promotionDTO.getEndDate());

        Promotion result = promotionRepository.save(promotion);
        return PromotionMapper.mapPromotionEntityToDTO(result);
    }

    @Override
    public boolean checkValidPromotion(String code) {
        Promotion promotion = promotionRepository.getPromotionByCode(code);

        LocalDateTime currentDate = LocalDateTime.now();
        if (currentDate.isBefore(promotion.getStartDate()) || currentDate.isAfter(promotion.getEndDate()) || promotion.getStatus().equals("INACTIVE"))
            return false;
        if (promotion.getUsageLimit() == 0) return false;
        return true;
    }
}
