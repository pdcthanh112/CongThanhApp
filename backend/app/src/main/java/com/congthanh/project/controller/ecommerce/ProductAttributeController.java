package com.congthanh.project.controller.ecommerce;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.ecommerce.ProductAttributeDTO;
import com.congthanh.project.model.ecommerce.response.Response;
import com.congthanh.project.service.ecommerce.ProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ecommerce/product-attribute")
public class ProductAttributeController {

    @Autowired
    private ProductAttributeService productAttributeService;

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
