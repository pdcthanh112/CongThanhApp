package com.congthanh.project.serviceImpl.ecommerce;

import com.congthanh.project.dto.ProductAttributeDTO;
import com.congthanh.project.entity.ProductAttribute;
import com.congthanh.project.model.ecommerce.mapper.ProductAttributeMapper;
import com.congthanh.project.repository.productAttribute.ProductAttributeRepository;
import com.congthanh.project.service.ecommerce.ProductAttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductAttributeServiceImpl implements ProductAttributeService {

    private final ProductAttributeRepository productAttributeRepository;

    @Override
    public ProductAttributeDTO createProductAttribute(ProductAttributeDTO productAttributeDTO) {
        ProductAttribute productAttribute = ProductAttribute.builder()
                .name(productAttributeDTO.getName())
                .build();
        ProductAttribute result = productAttributeRepository.save(productAttribute);
        return ProductAttributeMapper.mapProductAttributeEntityToDTO(result);
    }
}
