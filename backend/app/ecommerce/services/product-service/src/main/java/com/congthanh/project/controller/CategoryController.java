package com.congthanh.project.controller;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.CategoryDTO;
import com.congthanh.project.model.response.Response;
import com.congthanh.project.entity.Category;
import com.congthanh.project.repository.category.CategoryRepository;
import com.congthanh.project.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
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
  public ResponseEntity<Response<CategoryDTO>> getCategoryById(@PathVariable("id") int id) {
    CategoryDTO data = categoryService.getCategoryById(id);
    Response<CategoryDTO> response = new Response<>();
    response.setData(data);
    response.setMessage("Get by id successfully");
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/create")
  @PermitAll
  public ResponseEntity<Response<CategoryDTO>> createCategory(@RequestBody CategoryDTO categoryDTO) {
    CategoryDTO data = categoryService.createCategory(categoryDTO);
    Response<CategoryDTO> response = new Response<>();
    response.setData(data);
    response.setMessage("Create successfully");
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/update")
  public ResponseEntity<Response<CategoryDTO>> updateCategory(@RequestBody CategoryDTO categoryDTO) {
    CategoryDTO data = categoryService.updateCategory(categoryDTO);
    Response<CategoryDTO> response = new Response<>();
    response.setData(data);
    response.setMessage("Create successfully");
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    return ResponseEntity.ok().body(response);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteCategory(@RequestParam("id") int id) {
    boolean result = categoryService.deleteCategory(id);
    return ResponseEntity.status(HttpStatus.OK).body("Delete successfully");
  }

  @QueryMapping(value = "category")
  List<Category> categories() {
    return categoryRepository.findAll();
  }
}
