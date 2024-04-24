package com.congthanh.project.controller.ecommerce;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.ecommerce.AttributeValueDTO;
import com.congthanh.project.model.ecommerce.request.ProductAttributeValueRequest;
import com.congthanh.project.model.ecommerce.response.Response;
import com.congthanh.project.service.ecommerce.AttributeValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/attribute-value")
public class AttributeValueController {

    @Autowired
    private AttributeValueService attributeValueService;

    @GetMapping("/getByProduct")
    public ResponseEntity<Response<List<AttributeValueDTO>>> getAttributeByProduct(@RequestParam("product") String productId) {
        List<AttributeValueDTO> data = attributeValueService.getAttributeByProduct(productId);
        Response<List<AttributeValueDTO>> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("get successfully");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<Response<AttributeValueDTO>> createAttributeValue(@RequestBody ProductAttributeValueRequest request) {
        AttributeValueDTO result = attributeValueService.createAttributeValue(request);
        Response<AttributeValueDTO> response = new Response<>();
        response.setData(result);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Response<AttributeValueDTO>> updateAttributeValue(@RequestBody ProductAttributeValueRequest request) {
        AttributeValueDTO result = attributeValueService.updateAttributeValue(request);
        Response<AttributeValueDTO> response = new Response<>();
        response.setData(result);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("updated successfully");
        return ResponseEntity.ok().body(response);
    }

}
