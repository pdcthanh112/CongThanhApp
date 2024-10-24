package com.congthanh.project.service;

import com.congthanh.project.dto.CategoryDTO;
import com.congthanh.project.model.request.CreateCategoryRequest;

public interface CategoryService {

  Object getAllCategory(Integer pageNo, Integer pageSize);

  CategoryDTO getCategoryById(String id);

  CategoryDTO createCategory(CreateCategoryRequest category);

  CategoryDTO updateCategory(CategoryDTO categoryDTO);

  boolean deleteCategory(String id);

}
