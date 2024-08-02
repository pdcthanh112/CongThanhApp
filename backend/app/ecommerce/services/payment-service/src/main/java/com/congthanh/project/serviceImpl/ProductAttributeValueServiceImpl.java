package com.congthanh.project.serviceImpl;

import com.congthanh.project.dto.ProductAttributeValueDTO;
import com.congthanh.project.entity.ecommerce.ProductAttributeValue;
import com.congthanh.project.entity.ecommerce.Product;
import com.congthanh.project.entity.ecommerce.ProductAttribute;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.model.mapper.ProductAttributeValueMapper;
import com.congthanh.project.model.request.ProductAttributeValueRequest;
import com.congthanh.project.repository.productAtrributeValue.ProductAttributeValueRepository;
import com.congthanh.project.repository.product.ProductRepository;
import com.congthanh.project.repository.productAttribute.ProductAttributeRepository;
import com.congthanh.project.service.ProductAttributeValueService;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAttributeValueServiceImpl implements ProductAttributeValueService {

    private final ProductAttributeValueRepository productAttributeValueRepository;

    private final ProductAttributeRepository productAttributeRepository;

    private final ProductRepository productRepository;

    @Override
    public List<ProductAttributeValueDTO> getAttributeByProduct(String productId) {
        List<Tuple> data = productAttributeValueRepository.getAttributeOfProduct(productId);
        if(data == null) {
            return null;
        }
        List<ProductAttributeValueDTO> result = new ArrayList<>();
        for (Tuple item: data) {
            ProductAttributeValueDTO valueDTO = ProductAttributeValueDTO.builder()
                    .id(item.get("id", Long.class))
//                    .attribute(ProductAttributeDTO.builder()
//                            .id(item.get("attributeId", Long.class))
//                            .name(item.get("name", String.class))
//                            .build())
                    .product(item.get("product", String.class))
                    .value(item.get("value", String.class))
                    .build();
            result.add(valueDTO);
        }
        return result;
    }

    @Override
    public ProductAttributeValueDTO createAttributeValue(ProductAttributeValueRequest request) {
        ProductAttribute attribute = productAttributeRepository.findById(request.getAttribute()).orElseThrow(() -> new NotFoundException("attribute not found"));
        Product product = productRepository.findById(request.getProduct()).orElseThrow(() -> new NotFoundException("product not found"));
        ProductAttributeValue productAttributeValue = ProductAttributeValue.builder()
                .attribute(attribute)
                .product(product)
                .value(request.getValue())
                .build();
        ProductAttributeValue result = productAttributeValueRepository.save(productAttributeValue);
        return ProductAttributeValueMapper.mapProductAttributeValueEntityToDTO(result);
    }

    @Override
    public ProductAttributeValueDTO updateAttributeValue(ProductAttributeValueRequest request) {
        ProductAttributeValue productAttributeValue = productAttributeValueRepository.findProductAttributeValueOfProduct(request.getAttribute(), request.getProduct());
        if (productAttributeValue == null) {
            throw new NotFoundException("attribute value not found");
        }
        productAttributeValue.setValue(request.getValue());
        ProductAttributeValue result = productAttributeValueRepository.save(productAttributeValue);
        return ProductAttributeValueMapper.mapProductAttributeValueEntityToDTO(result);
    }
}
