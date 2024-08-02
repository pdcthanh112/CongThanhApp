package com.congthanh.project.service;

import com.congthanh.project.dto.SubcategoryDTO;
import com.congthanh.project.entity.ecommerce.Subcategory;

import java.util.List;

public interface SubcategoryService {

  Object getAllSubcategory(Integer pageNo, Integer pageSize);

  SubcategoryDTO getSubcategoryById(int id);

  Subcategory createSubcategory(String name, int categoryId);

  Subcategory updateSubcategory(SubcategoryDTO subcategoryDTO);

  boolean deleteSubcategory(int id);

  List<SubcategoryDTO> getSubcategoryByCategoryId(int id);
}
