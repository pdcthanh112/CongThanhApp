package com.congthanh.catalogservice.service;

import com.congthanh.catalogservice.model.dto.CategoryDTO;
import com.congthanh.catalogservice.model.request.AddSubcategoryRequest;
import com.congthanh.catalogservice.model.request.CreateCategoryRequest;
import com.congthanh.catalogservice.model.request.UpdateCategoryRequest;

public interface CategoryService {

  Object getAllCategory(Integer pageNo, Integer pageSize);

  CategoryDTO getCategoryById(String id);

  CategoryDTO createCategory(CreateCategoryRequest category);

  CategoryDTO updateCategory(UpdateCategoryRequest request, String categoryId);

  void deleteCategory(String id);

  void addSubcategory(AddSubcategoryRequest data, String parentId);

  void removeSubcategory(String categoryId, String parentId);
}
