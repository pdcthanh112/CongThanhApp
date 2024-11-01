package com.congthanh.project.controller;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.CategoryDTO;
import com.congthanh.project.entity.Category;
import com.congthanh.project.model.request.CreateCategoryRequest;
import com.congthanh.project.model.request.UpdateCategoryRequest;
import com.congthanh.project.model.response.Response;
import com.congthanh.project.repository.category.CategoryRepository;
import com.congthanh.project.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/category")
@Tag(name = "Category API", description = "Category API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryRepository categoryRepository;

  private final CategoryService categoryService;

  @GetMapping("/getAll")
  //@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
  @PermitAll
  public ResponseEntity<Response<Object>> getAllCategory(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
    Object data = categoryService.getAllCategory(page, limit);
    Response<Object> response = new Response<>();
    response.setData(data);
    response.setMessage("Get all successfully");
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Response<CategoryDTO>> getCategoryById(@PathVariable("id") String id) {
    CategoryDTO data = categoryService.getCategoryById(id);
    Response<CategoryDTO> response = new Response<>();
    response.setData(data);
    response.setMessage("Get by id successfully");
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/create")
  @PermitAll
  public ResponseEntity<Response<CategoryDTO>> createCategory(@RequestBody @Valid CreateCategoryRequest request) {
    CategoryDTO data = categoryService.createCategory(request);
    Response<CategoryDTO> response = new Response<>();
    response.setData(data);
    response.setMessage("Create successfully");
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<Response<CategoryDTO>> updateCategory(@RequestBody @Valid UpdateCategoryRequest request, @PathVariable("id") String categoryId) {
    CategoryDTO data = categoryService.updateCategory(request, categoryId);
    Response<CategoryDTO> response = new Response<>();
    response.setData(data);
    response.setMessage("Updated successfully");
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    return ResponseEntity.ok().body(response);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteCategory(@RequestParam("id") String id) {
    boolean result = categoryService.deleteCategory(id);
    return ResponseEntity.status(HttpStatus.OK).body("Delete successfully");
  }

  @QueryMapping(value = "category")
  List<Category> categories() {
    return categoryRepository.findAll();
  }
}
