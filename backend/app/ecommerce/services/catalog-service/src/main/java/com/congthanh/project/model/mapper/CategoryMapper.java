package com.congthanh.project.model.mapper;

import com.congthanh.project.dto.CategoryDTO;
import com.congthanh.project.entity.Category;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    private static  final  ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void configureModelMapper() {

    }

    public static Category mapCategoryDTOToEntity(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }

    public static CategoryDTO mapCategoryEntityToDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }
}