package com.congthanh.project.model.mapper;

import com.congthanh.project.model.dto.ProductVariantDTO;
import com.congthanh.project.model.dto.ProductVariantImageDTO;
import com.congthanh.project.model.entity.ProductVariant;
import com.congthanh.project.model.entity.ProductVariantImage;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductVariantMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true);
    }

    @PostConstruct
    private void configureModelMapper() {
        modelMapper.typeMap(ProductVariant.class, ProductVariantDTO.class).addMapping(src -> src.getProduct().getId(), ProductVariantDTO::setProduct);
    }

    public static ProductVariant mapProductVariantDTOToEntity(ProductVariantDTO productVariantDto) {
        return modelMapper.map(productVariantDto, ProductVariant.class);
    }

    public static ProductVariantDTO mapProductVariantEntityToDTO(ProductVariant productVariant) {
        ProductVariantDTO productVariantDTO = modelMapper.map(productVariant, ProductVariantDTO.class);
        productVariantDTO.setImage(mapProductVariantImageListToProductVariantImageDTOList(productVariant.getImage()));
        return productVariantDTO;
    }

    private static List<ProductVariantImageDTO> mapProductVariantImageListToProductVariantImageDTOList(Set<ProductVariantImage> productVariantImages) {
        return productVariantImages.stream()
                .map(variantImage -> ProductVariantImageMapper.mapProductVariantImageEntityToDTO(variantImage))
                .collect(Collectors.toList());
    }
}
