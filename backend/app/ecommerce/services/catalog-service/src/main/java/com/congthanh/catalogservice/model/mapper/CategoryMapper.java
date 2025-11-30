package com.congthanh.catalogservice.model.mapper;

import com.congthanh.catalogservice.model.dto.CategoryDTO;
import com.congthanh.catalogservice.model.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

//    @Mapping(target = "id", source = "id")
    Category mapCategoryDTOToEntity(CategoryDTO categoryDTO);

    CategoryDTO mapCategoryEntityToDTO(Category category);
}