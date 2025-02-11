package com.congthanh.productservice.controller;

import com.congthanh.productservice.constant.common.ResponseStatus;
import com.congthanh.productservice.model.dto.ProductDTO;
import com.congthanh.productservice.model.request.CreateProductRequest;
import com.congthanh.productservice.model.dto.ProductVariantAttributeValueDTO;
import com.congthanh.productservice.model.response.Response;
import com.congthanh.productservice.model.response.ResponseWithPagination;
import com.congthanh.productservice.model.entity.Product;
import com.congthanh.productservice.model.viewmodel.ProductVm;
import com.congthanh.productservice.repository.product.ProductRepository;
import com.congthanh.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/products")
@Tag(name = "Product API", description = "Product API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductRepository productRepository;

    private final ProductService productService;

    @GetMapping("")
    @PermitAll
    public ResponseEntity<Response<?>> getAllProduct(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        Object data = productService.getAllProduct(page, limit);
        Response<Object> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get all successfully");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    @PermitAll
    public ResponseEntity<Response<ProductDTO>> getProductById(@PathVariable("id") String id) {
        ProductDTO data = productService.getProductById(id);
        Response<ProductDTO> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get by id successfully");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/detail/{id}")
    @PermitAll
    @Operation(
            summary = "Get product detail by Id",
            description = "Get product detail by id with full attribute",
            tags = {"product", "get", "detail"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    public ResponseEntity<Response<ProductDTO>> getProductDetailById(@PathVariable("id") String id) {
        ProductDTO data = productService.getProductDetailById(id);
        Response<ProductDTO> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get detail by id successfully");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/back-office/slug/{slug}")
    @PermitAll
    public ResponseEntity<Response<ProductDTO>> getProductDTOBySlug(@PathVariable("slug") String slug) {
        ProductDTO data = productService.getProductDTOBySlug(slug);
        Response<ProductDTO> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get by slug successfully");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/store-front/slug/{slug}")
    @PermitAll
    public ResponseEntity<Response<ProductVm>> getProductVmBySlug(@PathVariable("slug") String slug) {
        ProductVm data = productService.getProductVmBySlug(slug);
        Response<ProductVm> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get by slug successfully");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    @PermitAll
    public ResponseEntity<Response<ProductDTO>> createProduct(@RequestBody @Validated CreateProductRequest request) {
        ProductDTO data = productService.createProduct(request);
        Response<ProductDTO> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestBody ProductDTO productDTO) {
        Product product = productService.updateProduct(productDTO);
        return ResponseEntity.ok().body("Update successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") String id) {
        boolean result = productService.deleteProduct(id);
        return ResponseEntity.ok().body("Delete successfully");
    }

    @GetMapping("/store-front/categories/{categoryId}/products")
    public ResponseEntity<Response<ResponseWithPagination<ProductDTO>>> getProductByCategory(@PathVariable("categoryId") String categoryId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) {
        ResponseWithPagination<ProductDTO> data = productService.getProductByCategoryId(categoryId, page, limit);
        Response<ResponseWithPagination<ProductDTO>> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get successfully");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/store-front/categories/slug/{slug}/products")
    public ResponseEntity<Response<ResponseWithPagination<ProductDTO>>> getProductByCategorySlug(@PathVariable("slug") String slug, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) {
        ResponseWithPagination<ProductDTO> data = productService.getProductByCategorySlug(slug, page, limit);
        Response<ResponseWithPagination<ProductDTO>> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get successfully");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Response<List<ProductDTO>>> searchProduct(@RequestParam String search) {
        List<ProductDTO> result = productService.searchProduct(search);
        Response<List<ProductDTO>> response = new Response<>();
        response.setData(result);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get successfully");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/sold/{id}")
    public ResponseEntity<Response<Long>> getSoldByProduct(@PathVariable("id") String productId) {
        Long result = productService.getSoldByProduct(productId);
        Response<Long> response = new Response<>();
        response.setData(result);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get successfully");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/get-variant-attribute")
    public ResponseEntity<Response<List<ProductVariantAttributeValueDTO>>> getVariantAttributeValueByProduct(@RequestParam("product") String productId) {
        List<ProductVariantAttributeValueDTO> result = productService.getVariantAttributeValueByProduct(productId);
        Response<List<ProductVariantAttributeValueDTO>> response = new Response<>();
        response.setData(result);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get successfully");
        return ResponseEntity.ok().body(response);
    }

    @QueryMapping(value = "product")
    List<Product> products() {
        return productRepository.findAll();
    }
}
