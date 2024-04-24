package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.ecommerce.AttributeValueDTO;
import com.congthanh.project.entity.ecommerce.AttributeValue;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttributeValueMapper {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    private void configureModelMapper() {

        modelMapper.typeMap(AttributeValue.class, AttributeValueDTO.class)
//                .addMapping(src -> src.getAttribute().getId(), AttributeValueDTO::setAttribute)
                .addMapping(src -> src.getProduct().getId(), AttributeValueDTO::setProduct);
    }

    public AttributeValue mapAttributeValueDTOToEntity(AttributeValueDTO attributeValueDTO) {
        return modelMapper.map(attributeValueDTO, AttributeValue.class);
    }

    public AttributeValueDTO mapAttributeValueEntityToDTO(AttributeValue attributeValue) {
        return modelMapper.map(attributeValue, AttributeValueDTO.class);
    }

}
