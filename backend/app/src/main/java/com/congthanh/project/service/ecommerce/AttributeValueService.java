package com.congthanh.project.service.ecommerce;

import com.congthanh.project.dto.ecommerce.AttributeValueDTO;
import com.congthanh.project.model.ecommerce.request.ProductAttributeValueRequest;

import java.util.List;

public interface AttributeValueService {

    List<AttributeValueDTO> getAttributeByProduct(String productId);

    AttributeValueDTO createAttributeValue(ProductAttributeValueRequest productAttributeValueRequest);

    AttributeValueDTO updateAttributeValue(ProductAttributeValueRequest productAttributeValueRequest);

}
