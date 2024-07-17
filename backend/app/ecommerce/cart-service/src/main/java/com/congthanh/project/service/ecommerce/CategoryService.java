package com.congthanh.project.service.ecommerce;

import com.congthanh.project.dto.ecommerce.CategoryDTO;
import com.congthanh.project.entity.ecommerce.Category;

public interface CategoryService {

  Object getAllCategory(Integer pageNo, Integer pageSize);

  CategoryDTO getCategoryById(int id);

  CategoryDTO createCategory(CategoryDTO categoryDTO);

  CategoryDTO updateCategory(CategoryDTO categoryDTO);

  boolean deleteCategory(int id);

}
