package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.ecommerce.CategoryDTO;
import com.congthanh.project.entity.ecommerce.Category;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    private void configureModelMapper() {

          }

    public Category mapCategoryDTOToEntity(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }

    public CategoryDTO mapCategoryEntityToDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }
}
