package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.ecommerce.SubcategoryDTO;
import com.congthanh.project.entity.ecommerce.Subcategory;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubcategoryMapper {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    private void configureModelMapper() {

    }

    public SubcategoryMapper mapSubcategoryDTOToEntity(SubcategoryDTO subcategoryDTO) {
        return modelMapper.map(subcategoryDTO, SubcategoryMapper.class);
    }

    public SubcategoryDTO mapSubcategoryEntityToDTO(Subcategory subcategory) {
        return modelMapper.map(subcategory, SubcategoryDTO.class);
    }
}
