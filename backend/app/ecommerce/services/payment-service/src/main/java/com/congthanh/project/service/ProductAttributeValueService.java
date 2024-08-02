package com.congthanh.project.service;

import com.congthanh.project.dto.ProductAttributeValueDTO;
import com.congthanh.project.model.request.ProductAttributeValueRequest;

import java.util.List;

public interface ProductAttributeValueService {

    List<ProductAttributeValueDTO> getAttributeByProduct(String productId);

    ProductAttributeValueDTO createAttributeValue(ProductAttributeValueRequest productAttributeValueRequest);

    ProductAttributeValueDTO updateAttributeValue(ProductAttributeValueRequest productAttributeValueRequest);

}
