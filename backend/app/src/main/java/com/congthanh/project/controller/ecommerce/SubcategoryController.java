package com.congthanh.project.controller.ecommerce;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.ecommerce.SubcategoryDTO;
import com.congthanh.project.model.ecommerce.response.Response;
import com.congthanh.project.entity.ecommerce.Subcategory;
import com.congthanh.project.repository.ecommerce.subcategory.SubcategoryRepository;
import com.congthanh.project.service.ecommerce.SubcategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ecommerce/subcategory")
@Tag(name = "Subcategory API", description = "Subcategory API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class SubcategoryController {

  private final SubcategoryRepository subcategoryRepository;

  private final SubcategoryService subcategoryService;

  @GetMapping("getAll")
  //@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
  @PermitAll
  public ResponseEntity<Response<Object>> getAllSubcategory(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
    Object data = subcategoryService.getAllSubcategory(page, limit);
    Response<Object> response = new Response<>();
    response.setData(data);
    response.setMessage("Get all successfully");
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Response<SubcategoryDTO>> getSubcategoryById(@PathVariable("id") int id) {
    SubcategoryDTO data = subcategoryService.getSubcategoryById(id);
    Response<SubcategoryDTO> response = new Response<>();
    response.setData(data);
    response.setMessage("Get by id successfully");
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/create")
  public ResponseEntity<Response> createSubcategory(@RequestBody Map<String, Object> createSubcategoryModel) {
    String name = (String) createSubcategoryModel.get("name");
    int categoryId = (int) createSubcategoryModel.get("categoryId");
    Subcategory subcategory = subcategoryService.createSubcategory(name, categoryId);
    Response<Object> response = new Response<>();
    response.setData(subcategory);
    response.setMessage("Create successfully");
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/update")
  public ResponseEntity<String> updateSubcategory(@RequestBody SubcategoryDTO subcategoryDTO) {
    Subcategory subcategory = subcategoryService.updateSubcategory(subcategoryDTO);
    return ResponseEntity.status(HttpStatus.OK).body("Update successfully");
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteSubcategory(@RequestParam("id") int id) {
    boolean result = subcategoryService.deleteSubcategory(id);
    return ResponseEntity.status(HttpStatus.OK).body("Delete successfully");
  }

  @GetMapping("/getByCategory")
  public ResponseEntity<Response<List<SubcategoryDTO>>> getSubcategoryByCategoryId(@RequestParam int id) {
    List<SubcategoryDTO> data = subcategoryService.getSubcategoryByCategoryId(id);
    Response<List<SubcategoryDTO>> response = new Response<>();
    response.setData(data);
    response.setMessage("get successfully");
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    return ResponseEntity.ok().body(response);
  }

  @QueryMapping(value = "subcategory")
  List<Subcategory> subcategories() {
    return subcategoryRepository.findAll();
  }
}
