package com.congthanh.project.serviceImpl.ecommerce;

import com.congthanh.project.dto.ecommerce.ProductAttributeDTO;
import com.congthanh.project.entity.ecommerce.ProductAttribute;
import com.congthanh.project.model.ecommerce.mapper.ProductAttributeMapper;
import com.congthanh.project.repository.ecommerce.productAttribute.ProductAttributeRepository;
import com.congthanh.project.service.ecommerce.ProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    @Autowired
    private ProductAttributeMapper productAttributeMapper;

    @Override
    public ProductAttributeDTO createProductAttribute(ProductAttributeDTO productAttributeDTO) {
        ProductAttribute productAttribute = ProductAttribute.builder()
                .name(productAttributeDTO.getName())
                .build();
        ProductAttribute result = productAttributeRepository.save(productAttribute);
        return productAttributeMapper.mapProductAttributeEntityToDTO(result);
    }
}
