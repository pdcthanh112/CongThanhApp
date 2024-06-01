package com.congthanh.project.serviceImpl.ecommerce;

import com.congthanh.project.dto.ecommerce.ProductVariantDTO;
import com.congthanh.project.entity.ecommerce.ProductVariant;
import com.congthanh.project.repository.ecommerce.product.ProductRepository;
import com.congthanh.project.repository.ecommerce.productVariant.ProductVariantRepository;
import com.congthanh.project.service.ecommerce.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductVariantDTO> getProductVariantByProductId(String productId) {
        List<ProductVariant> result = productRepository.getVariantByProductId(productId);
        List<ProductVariantDTO> response = new ArrayList<>();
        if (!result.isEmpty()) {
            for (ProductVariant item : result) {
                ProductVariantDTO productVariantDTO = ProductVariantDTO.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .price(item.getPrice())
                        .build();
                response.add(productVariantDTO);
            }
        }
        return response;
    }
}
