package com.congthanh.productservice.model.mapper;

import com.congthanh.productservice.model.dto.ProductAttributeValueDTO;
import com.congthanh.productservice.model.entity.ProductAttributeValue;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class ProductAttributeValueMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true);
    }

    @PostConstruct
    private void configureModelMapper() {
        modelMapper.typeMap(ProductAttributeValue.class, ProductAttributeValueDTO.class)
                .addMapping(src -> src.getAttribute().getName(), ProductAttributeValueDTO::setAttribute)
                .addMapping(src -> src.getProduct().getId(), ProductAttributeValueDTO::setProduct);
        modelMapper.typeMap(ProductAttributeValueDTO.class, ProductAttributeValue.class).addMappings(mapper -> {
            mapper.skip(ProductAttributeValue::setAttribute);
        });
    }

    public static ProductAttributeValue mapProductAttributeValueDTOToEntity(ProductAttributeValueDTO productAttributeValueDTO) {
        return modelMapper.map(productAttributeValueDTO, ProductAttributeValue.class);
    }

    public static ProductAttributeValueDTO mapProductAttributeValueEntityToDTO(ProductAttributeValue productAttributeValue) {
        return modelMapper.map(productAttributeValue, ProductAttributeValueDTO.class);
    }
}
