package com.congthanh.project.service;

import com.congthanh.project.dto.CategoryDTO;
import com.congthanh.project.model.request.CreateCategoryRequest;
import com.congthanh.project.model.request.UpdateCategoryRequest;

public interface CategoryService {

  Object getAllCategory(Integer pageNo, Integer pageSize);

  CategoryDTO getCategoryById(String id);

  CategoryDTO createCategory(CreateCategoryRequest category);

  CategoryDTO updateCategory(UpdateCategoryRequest request, String categoryId);

  boolean deleteCategory(String id);

}
