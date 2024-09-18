package com.congthanh.project.controller;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.cqrs.command.service.ProductCommandService;
import com.congthanh.project.cqrs.query.service.ProductQueryService;
import com.congthanh.project.dto.ProductDTO;
import com.congthanh.project.model.request.CreateProductRequest;
import com.congthanh.project.dto.ProductVariantAttributeValueResponse;
import com.congthanh.project.model.response.Response;
import com.congthanh.project.model.response.ResponseWithPagination;
import com.congthanh.project.entity.Product;
import com.congthanh.project.repository.product.ProductRepository;
import com.congthanh.project.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/product")
@Tag(name = "Product API", description = "Product API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductRepository productRepository;

    private final ProductService productService;

    private final ProductCommandService productCommandService;

    private final ProductQueryService productQueryService;

    private final CommandGateway commandGateway;

    private final QueryGateway queryGateway;

    @GetMapping("/getAll")
    @PermitAll
    public ResponseEntity<Response<?>> getAllProduct(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        Object data = productService.getAllProduct(page, limit);
        Response<Object> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get all successfully");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getById/{id}")
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

    @GetMapping("/getBySlug/{slug}")
    @PermitAll
    public ResponseEntity<Response<ProductDTO>> getProductBySlug(@PathVariable("slug") String slug) {
        ProductDTO data = productService.getProductBySlug(slug);
        Response<ProductDTO> response = new Response<>();
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
    public ResponseEntity<String> deleteProduct(@RequestParam("id") String id) {
        boolean result = productService.deleteProduct(id);
        return ResponseEntity.ok().body("Delete successfully");
    }

//    @GetMapping("/getByCategory")
//    public ResponseEntity<Response<ResponseWithPagination<ProductDTO>>> getProductByCategory(@RequestParam int categoryId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit) {
//        ResponseWithPagination<ProductDTO> data = productService.getProductByCategory(categoryId, page, limit);
//        Response<ResponseWithPagination<ProductDTO>> response = new Response<>();
//        response.setData(data);
//        response.setStatus(ResponseStatus.STATUS_SUCCESS);
//        response.setMessage("Get successfully");
//        return ResponseEntity.ok().body(response);
//    }
//
//    @GetMapping("/getBySubcategory")
//    public ResponseEntity<Response<ResponseWithPagination<ProductDTO>>> getProductBySubcategory(@RequestParam int subcategory, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit) {
//        ResponseWithPagination<ProductDTO> data = productService.getProductBySubcategory(subcategory, page, limit);
//        Response<ResponseWithPagination<ProductDTO>> response = new Response<>();
//        response.setData(data);
//        response.setStatus(ResponseStatus.STATUS_SUCCESS);
//        response.setMessage("Get successfully");
//        return ResponseEntity.ok().body(response);
//    }

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
    public ResponseEntity<Response<List<ProductVariantAttributeValueResponse>>> getVariantAttributeValueByProduct(@RequestParam("product") String productId) {
        List<ProductVariantAttributeValueResponse> result = productService.getVariantAttributeValueByProduct(productId);
        Response<List<ProductVariantAttributeValueResponse>> response = new Response<>();
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
