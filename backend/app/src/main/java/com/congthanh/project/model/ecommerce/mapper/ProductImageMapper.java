package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.ecommerce.ProductImageDTO;
import com.congthanh.project.entity.ecommerce.ProductImage;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductImageMapper {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    private void configureModelMapper() {

    }

    public ProductImage mapProductImageDTOToEntity(ProductImageDTO productImageDTO) {
        return modelMapper.map(productImageDTO, ProductImage.class);
    }

    public ProductImageDTO mapProductImageEntityToDTO(ProductImage productImage) {
        return modelMapper.map(productImage, ProductImageDTO.class);
    }

}
