package com.congthanh.project.controller.ecommerce;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.ecommerce.ProductAttributeValueDTO;
import com.congthanh.project.model.ecommerce.request.ProductAttributeValueRequest;
import com.congthanh.project.model.ecommerce.response.Response;
import com.congthanh.project.service.ecommerce.ProductAttributeValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/attribute-value")
public class ProductAttributeValueController {

    @Autowired
    private ProductAttributeValueService productAttributeValueService;

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
    public ResponseEntity<Response<ProductAttributeValueDTO>> createAttributeValue(@RequestBody ProductAttributeValueRequest request) {
        ProductAttributeValueDTO result = productAttributeValueService.createAttributeValue(request);
        Response<ProductAttributeValueDTO> response = new Response<>();
        response.setData(result);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Response<ProductAttributeValueDTO>> updateAttributeValue(@RequestBody ProductAttributeValueRequest request) {
        ProductAttributeValueDTO result = productAttributeValueService.updateAttributeValue(request);
        Response<ProductAttributeValueDTO> response = new Response<>();
        response.setData(result);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("updated successfully");
        return ResponseEntity.ok().body(response);
    }

}
