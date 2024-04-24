package com.congthanh.project.serviceImpl.ecommerce;

import com.congthanh.project.dto.ecommerce.AttributeValueDTO;
import com.congthanh.project.dto.ecommerce.ProductAttributeDTO;
import com.congthanh.project.entity.ecommerce.AttributeValue;
import com.congthanh.project.entity.ecommerce.Product;
import com.congthanh.project.entity.ecommerce.ProductAttribute;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.model.ecommerce.mapper.AttributeValueMapper;
import com.congthanh.project.model.ecommerce.request.ProductAttributeValueRequest;
import com.congthanh.project.repository.ecommerce.attributeValue.AttributeValueRepository;
import com.congthanh.project.repository.ecommerce.product.ProductRepository;
import com.congthanh.project.repository.ecommerce.productAttribute.ProductAttributeRepository;
import com.congthanh.project.service.ecommerce.AttributeValueService;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttributeValueServiceImpl implements AttributeValueService {

    @Autowired
    private AttributeValueRepository attributeValueRepository;

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AttributeValueMapper attributeValueMapper;

    @Override
    public List<AttributeValueDTO> getAttributeByProduct(String productId) {
        List<Tuple> data = attributeValueRepository.getAttributeOfProduct(productId);
        if(data == null) {
            return null;
        }
        List<AttributeValueDTO> result = new ArrayList<>();
        for (Tuple item: data) {
            AttributeValueDTO valueDTO = AttributeValueDTO.builder()
                    .id(item.get("id", Long.class))
                    .attribute(ProductAttributeDTO.builder()
                            .id(item.get("attributeId", Long.class))
                            .name(item.get("name", String.class))
                            .build())
                    .product(item.get("product", String.class))
                    .value(item.get("value", String.class))
                    .build();
            result.add(valueDTO);
        }
        return result;
    }

    @Override
    public AttributeValueDTO createAttributeValue(ProductAttributeValueRequest request) {
        ProductAttribute attribute = productAttributeRepository.findById(request.getAttribute()).orElseThrow(() -> new NotFoundException("attribute not found"));
        Product product = productRepository.findById(request.getProduct()).orElseThrow(() -> new NotFoundException("product not found"));
        AttributeValue attributeValue = AttributeValue.builder()
                .attribute(attribute)
                .product(product)
                .value(request.getValue())
                .build();
        AttributeValue result = attributeValueRepository.save(attributeValue);
        return attributeValueMapper.mapAttributeValueEntityToDTO(result);
    }

    @Override
    public AttributeValueDTO updateAttributeValue(ProductAttributeValueRequest request) {
        AttributeValue attributeValue = attributeValueRepository.findAttributeValueOfProduct(request.getAttribute(), request.getProduct());
        if (attributeValue == null) {
            throw new NotFoundException("attribute value not found");
        }
        attributeValue.setValue(request.getValue());
        AttributeValue result = attributeValueRepository.save(attributeValue);
        return attributeValueMapper.mapAttributeValueEntityToDTO(result);
    }
}
