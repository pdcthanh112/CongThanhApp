package com.congthanh.project.controller;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.ProductVariantDTO;
import com.congthanh.project.model.response.Response;
import com.congthanh.project.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/product-variant")
@RequiredArgsConstructor
public class ProductVariantController {

    private final ProductVariantService productVariantService;

    @GetMapping("/get-by-product/{id}")
    public ResponseEntity<Response<List<ProductVariantDTO>>> getProductVariantByProductId(@PathVariable("id") String productId) {
        List<ProductVariantDTO> data = productVariantService.getProductVariantByProductId(productId);
        Response<List<ProductVariantDTO>> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get all successfully");
        return ResponseEntity.ok().body(response);
    }
}
