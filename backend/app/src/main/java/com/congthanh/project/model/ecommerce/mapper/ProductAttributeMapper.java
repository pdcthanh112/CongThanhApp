package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.ecommerce.ProductAttributeDTO;
import com.congthanh.project.entity.ecommerce.ProductAttribute;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductAttributeMapper {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    private void configureModelMapper() {

    }

    public ProductAttribute mapProductAttributeDTOToEntity(ProductAttributeDTO productAttributeDTO) {
        return modelMapper.map(productAttributeDTO, ProductAttribute.class);
    }

    public ProductAttributeDTO mapProductAttributeEntityToDTO(ProductAttribute productAttribute) {
        return modelMapper.map(productAttribute, ProductAttributeDTO.class);
    }
}
