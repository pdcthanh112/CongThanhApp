package com.congthanh.messageservice.model.mapper;

import com.congthanh.catalogservice.model.dto.CategoryDTO;
import com.congthanh.catalogservice.model.entity.Category;
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