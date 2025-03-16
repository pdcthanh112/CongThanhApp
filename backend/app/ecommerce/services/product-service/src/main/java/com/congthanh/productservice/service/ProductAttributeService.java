package com.congthanh.productservice.service;

import com.congthanh.productservice.model.dto.attribute.ProductAttributeDTO;
import com.congthanh.productservice.model.request.ProductAttributeValueRequest;

import java.util.List;

public interface ProductAttributeService {

    List<ProductAttributeDTO> getAllProductAttribute();

    ProductAttributeDTO createProductAttribute(ProductAttributeDTO productAttributeDTO);

    void addAttributeToProduct(List<ProductAttributeValueRequest> request, String productId);
}
