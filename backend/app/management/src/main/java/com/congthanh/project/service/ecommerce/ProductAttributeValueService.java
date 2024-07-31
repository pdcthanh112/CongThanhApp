package com.congthanh.project.service.ecommerce;

import com.congthanh.project.dto.ecommerce.ProductAttributeValueDTO;
import com.congthanh.project.model.ecommerce.request.ProductAttributeValueRequest;

import java.util.List;

public interface ProductAttributeValueService {

    List<ProductAttributeValueDTO> getAttributeByProduct(String productId);

    ProductAttributeValueDTO createAttributeValue(ProductAttributeValueRequest productAttributeValueRequest);

    ProductAttributeValueDTO updateAttributeValue(ProductAttributeValueRequest productAttributeValueRequest);

}
