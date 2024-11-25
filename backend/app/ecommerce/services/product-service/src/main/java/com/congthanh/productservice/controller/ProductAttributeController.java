package com.congthanh.productservice.controller;

import com.congthanh.catalogservice.constant.common.ResponseStatus;
import com.congthanh.productservice.model.dto.ProductAttributeDTO;
import com.congthanh.catalogservice.model.response.Response;
import com.congthanh.productservice.service.ProductAttributeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ecommerce/product-attribute")
@Tag(name = "Product Attribute API", description = "Product attribute API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class ProductAttributeController {

    private final ProductAttributeService productAttributeService;

    @PostMapping("/create")
    public ResponseEntity<Response<ProductAttributeDTO>> createProductAttribute(@RequestBody ProductAttributeDTO productAttributeDTO) {
        ProductAttributeDTO data = productAttributeService.createProductAttribute(productAttributeDTO);
        Response<ProductAttributeDTO> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
