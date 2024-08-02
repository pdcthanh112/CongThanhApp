package com.congthanh.project.controller;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.ProductImageDTO;
import com.congthanh.project.model.response.Response;
import com.congthanh.project.service.ProductImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/product-image")
@Tag(name = "Product image API", description = "Product image API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService productImageService;

    @GetMapping("/getByProduct")
    public ResponseEntity<Response<List<ProductImageDTO>>>getImageByProduct(@RequestParam("product") String productId) {
        List<ProductImageDTO> data = productImageService.getImageByProduct(productId);
        Response<List<ProductImageDTO>> response = new Response<>();
        response.setData(data);
        response.setMessage("Get successfully");
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getDefaultImage")
    public ResponseEntity<Response<ProductImageDTO>> getDefaultImageByProduct(@RequestParam("product") String productId) {
        ProductImageDTO data = productImageService.getDefaultImageByProduct(productId);
        Response<ProductImageDTO> response = new Response<>();
        response.setData(data);
        response.setMessage("Get successfully");
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<Response<ProductImageDTO>> addProductImage(@RequestBody ProductImageDTO productImageDTO) {
        ProductImageDTO data = productImageService.addProductImage(productImageDTO);
        Response<ProductImageDTO> response = new Response<>();
        response.setData(data);
        response.setMessage("Create successfully");
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
