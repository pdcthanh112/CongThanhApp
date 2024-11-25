package com.congthanh.productservice.controller;

import com.congthanh.catalogservice.constant.common.ResponseStatus;
import com.congthanh.productservice.model.dto.ProductAttributeValueDTO;
import com.congthanh.productservice.model.request.ProductAttributeValueRequest;
import com.congthanh.catalogservice.model.response.Response;
import com.congthanh.productservice.service.ProductAttributeValueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/attribute-value")
@RequiredArgsConstructor
public class ProductAttributeValueController {

    private final ProductAttributeValueService productAttributeValueService;

    @GetMapping("/getByProduct")
    public ResponseEntity<Response<List<ProductAttributeValueDTO>>> getAttributeByProduct(@RequestParam("product") String productId) {
        List<ProductAttributeValueDTO> data = productAttributeValueService.getAttributeByProduct(productId);
        Response<List<ProductAttributeValueDTO>> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("get successfully");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<Response<ProductAttributeValueDTO>> createAttributeValue(@RequestBody @Valid ProductAttributeValueRequest request) {
        ProductAttributeValueDTO result = productAttributeValueService.createAttributeValue(request);
        Response<ProductAttributeValueDTO> response = new Response<>();
        response.setData(result);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Response<ProductAttributeValueDTO>> updateAttributeValue(@RequestBody @Valid ProductAttributeValueRequest request) {
        ProductAttributeValueDTO result = productAttributeValueService.updateAttributeValue(request);
        Response<ProductAttributeValueDTO> response = new Response<>();
        response.setData(result);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("updated successfully");
        return ResponseEntity.ok().body(response);
    }

}
