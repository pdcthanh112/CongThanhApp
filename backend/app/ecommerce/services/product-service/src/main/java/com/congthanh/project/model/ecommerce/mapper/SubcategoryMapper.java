package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.SubcategoryDTO;
import com.congthanh.project.entity.Subcategory;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SubcategoryMapper {

    private static  final  ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void configureModelMapper() {

    }

    public static SubcategoryMapper mapSubcategoryDTOToEntity(SubcategoryDTO subcategoryDTO) {
        return modelMapper.map(subcategoryDTO, SubcategoryMapper.class);
    }

    public static SubcategoryDTO mapSubcategoryEntityToDTO(Subcategory subcategory) {
        return modelMapper.map(subcategory, SubcategoryDTO.class);
    }
}
