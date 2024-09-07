package com.congthanh.project.model.mapper;

import com.congthanh.project.dto.PromotionDTO;
import com.congthanh.project.entity.Promotion;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PromotionMapper {

    private static  final  ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void configureModelMapper() {

    }

    public static Promotion mapPromotionDTOToEntity(PromotionDTO promotionDTO) {
        return modelMapper.map(promotionDTO, Promotion.class);
    }

    public static PromotionDTO mapPromotionEntityToDTO(Promotion promotion) {
        return modelMapper.map(promotion, PromotionDTO.class);
    }

}
