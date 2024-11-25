package com.congthanh.productservice.service.serviceImpl;

import com.congthanh.productservice.model.dto.ProductAttributeDTO;
import com.congthanh.productservice.model.entity.ProductAttribute;
import com.congthanh.productservice.model.mapper.ProductAttributeMapper;
import com.congthanh.productservice.repository.productAttribute.ProductAttributeRepository;
import com.congthanh.productservice.service.ProductAttributeService;
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
