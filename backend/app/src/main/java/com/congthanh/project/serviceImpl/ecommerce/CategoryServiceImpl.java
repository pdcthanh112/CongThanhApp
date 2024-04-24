package com.congthanh.project.serviceImpl.ecommerce;

import com.congthanh.project.constant.common.StateStatus;
import com.congthanh.project.dto.ecommerce.CategoryDTO;
import com.congthanh.project.model.ecommerce.response.ResponseWithPagination;
import com.congthanh.project.entity.ecommerce.Category;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.model.ecommerce.mapper.CategoryMapper;
import com.congthanh.project.repository.ecommerce.category.CategoryRepository;
import com.congthanh.project.service.ecommerce.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private CategoryMapper categoryMapper;

  @Override
  public Object getAllCategory(Integer pageNo, Integer pageSize) {
    if (pageNo != null && pageSize != null) {
      Pageable pageable = PageRequest.of(pageNo, pageSize);
      Page<Category> pageResult = categoryRepository.findAll(pageable);
      ResponseWithPagination<CategoryDTO> result = new ResponseWithPagination<>();
      List<CategoryDTO> list = new ArrayList<>();
      if (pageResult.hasContent()) {
        for (Category category : pageResult.getContent()) {
          CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
          list.add(categoryDTO);
        }
        result.setResponseList(list);
        result.setTotalPage(pageResult.getTotalPages());
      } else {
        throw new RuntimeException("List empty exception");
      }
      return result;
    } else {
      List<Category> list = categoryRepository.findAll();
      List<CategoryDTO> result = new ArrayList<>();
      for (Category item : list) {
        CategoryDTO categoryDTO = modelMapper.map(item, CategoryDTO.class);
        result.add(categoryDTO);
      }
      return result;
    }
  }

  @Override
  public CategoryDTO getCategoryById(int id) {
    Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(("not found")));
    return categoryMapper.mapCategoryEntityToDTO(category);
  }

  @Override
  public CategoryDTO createCategory(CategoryDTO categoryDTO) {
    Optional<Category> existCategory = categoryRepository.findByName(categoryDTO.getName());
    if (existCategory.isPresent()) {
      throw new RuntimeException("Category ton tai");
    } else {
      Category category = Category.builder()
              .name(categoryDTO.getName())
              .status(StateStatus.STATUS_ACTIVE)
              .build();
      Category result = categoryRepository.save(category);
      CategoryDTO response = categoryMapper.mapCategoryEntityToDTO(result);
      return response;
    }
  }

  @Override
  public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
    Category category = categoryRepository.findById(categoryDTO.getId()).orElseThrow(() -> new RuntimeException("Category not found"));

    category.setName(categoryDTO.getName());
    category.setImage(category.getImage());

    Category result = categoryRepository.save(category);
    return categoryMapper.mapCategoryEntityToDTO(result);
  }

  @Override
  public boolean deleteCategory(int id) {
    Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    if (category.getStatus().equalsIgnoreCase(StateStatus.STATUS_DELETED)) {
      throw new RuntimeException("Category have deleted before");
    } else {
      boolean result = categoryRepository.deleteCategory(id);
      return result;
    }
  }

}
