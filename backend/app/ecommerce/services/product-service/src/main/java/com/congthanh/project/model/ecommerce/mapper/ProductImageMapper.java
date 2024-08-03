package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.ProductImageDTO;
import com.congthanh.project.entity.ProductImage;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class ProductImageMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true);
    }

    @PostConstruct
    private void configureModelMapper() {
        modelMapper.typeMap(ProductImage.class, ProductImageDTO.class)
                .addMapping(src -> src.getProduct().getId(), ProductImageDTO::setProduct);
    }

    public static ProductImage mapProductImageDTOToEntity(ProductImageDTO productImageDTO) {
        return modelMapper.map(productImageDTO, ProductImage.class);
    }

    public static ProductImageDTO mapProductImageEntityToDTO(ProductImage productImage) {
        return modelMapper.map(productImage, ProductImageDTO.class);
    }

}
