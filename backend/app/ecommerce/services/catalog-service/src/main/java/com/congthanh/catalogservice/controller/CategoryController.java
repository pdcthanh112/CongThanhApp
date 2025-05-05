package com.congthanh.catalogservice.controller;

import com.congthanh.catalogservice.constant.common.ResponseStatus;
import com.congthanh.catalogservice.model.dto.CategoryDTO;
import com.congthanh.catalogservice.model.entity.Category;
import com.congthanh.catalogservice.model.request.AddSubcategoryRequest;
import com.congthanh.catalogservice.model.request.CreateCategoryRequest;
import com.congthanh.catalogservice.model.request.RequestFilter;
import com.congthanh.catalogservice.model.request.UpdateCategoryRequest;
import com.congthanh.catalogservice.model.response.Response;
import com.congthanh.catalogservice.model.response.ResponseWithPagination;
import com.congthanh.catalogservice.repository.category.CategoryRepository;
import com.congthanh.catalogservice.service.CategoryService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/categories")
@Tag(name = "Category API", description = "Category API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;

    private final CategoryService categoryService;

    @GetMapping("")
//    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @PermitAll
    public ResponseEntity<Response<Object>> getAllCategory(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
//        Object data = categoryService.getAllCategory(page, limit);
        RequestFilter filter = null;
        Object data = categoryService.getAllCategoryJson(filter);
        Response<Object> response = new Response<>();
        response.setData(data);
        response.setMessage("Get all successfully");
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/list")
    @PermitAll
    public ResponseEntity<Response<ResponseWithPagination<CategoryDTO>>> getAllCategoryList(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        ResponseWithPagination<CategoryDTO> data = categoryService.getAllCategory(page, limit);
        Response<ResponseWithPagination<CategoryDTO>> response = new Response<>();
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

    @PostMapping("/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = Error.class)))})
    public ResponseEntity<Response<CategoryDTO>> createCategory(@RequestBody @Valid CreateCategoryRequest request) {
        CategoryDTO data = categoryService.createCategory(request);
        Response<CategoryDTO> response = new Response<>();
        response.setData(data);
        response.setMessage("Create successfully");
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = Error.class)))})
    public ResponseEntity<Response<CategoryDTO>> updateCategory(@RequestBody @Valid UpdateCategoryRequest request, @PathVariable("id") String categoryId) {
        CategoryDTO data = categoryService.updateCategory(request, categoryId);
        Response<CategoryDTO> response = new Response<>();
        response.setData(data);
        response.setMessage("Updated successfully");
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = Error.class)))})
    public ResponseEntity<String> deleteCategory(@RequestParam("id") String id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).body("Delete successfully");
    }

    @PostMapping("/{id}/add-subcategory")
    public ResponseEntity<Response<CategoryDTO>> addSubcategory(@RequestBody @Valid AddSubcategoryRequest request, @PathVariable("id") String parentId) {
        categoryService.addSubcategory(request, parentId);
        Response<CategoryDTO> response = new Response<>();
        response.setData(null);
        response.setMessage("Add subcategory successfully");
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{parentId}/remove-subcategory/{categoryId}")
    public ResponseEntity<String> removeSubcategory(@PathVariable("parentId") String parentId, @PathVariable("categoryId") String categoryId) {
        categoryService.removeSubcategory(categoryId, parentId);
        return ResponseEntity.status(HttpStatus.OK).body("Delete successfully");
    }

    @QueryMapping(value = "categories")
    List<Category> categories() {
        return categoryRepository.findAll();
    }
}
