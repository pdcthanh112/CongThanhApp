package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.ecommerce.ProductVariantImageDTO;
import com.congthanh.project.entity.ecommerce.ProductVariantImage;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class ProductVariantImageMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true);
    }

    @PostConstruct
    private void configureModelMapper() {
        modelMapper.typeMap(ProductVariantImage.class, ProductVariantImageDTO.class)
                .addMapping(src -> src.getVariant().getId(), ProductVariantImageDTO::setVariant);
    }

    public static ProductVariantImage mapProductVariantImageDTOToEntity(ProductVariantImageDTO productVariantImageDTO) {
        return modelMapper.map(productVariantImageDTO, ProductVariantImage.class);
    }

    public static ProductVariantImageDTO mapProductVariantImageEntityToDTO(ProductVariantImage productVariantImage) {
        return modelMapper.map(productVariantImage, ProductVariantImageDTO.class);
    }
}
