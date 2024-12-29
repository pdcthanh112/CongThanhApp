package com.congthanh.productservice.service;

import com.congthanh.productservice.model.dto.ProductAttributeValueDTO;
import com.congthanh.productservice.model.request.ProductAttributeValueRequest;

import java.util.List;

public interface ProductAttributeValueService {

    List<ProductAttributeValueDTO> getAttributeByProduct(String productId);

    ProductAttributeValueDTO createAttributeValue(ProductAttributeValueRequest productAttributeValueRequest);

    ProductAttributeValueDTO updateAttributeValue(ProductAttributeValueRequest productAttributeValueRequest);

}
