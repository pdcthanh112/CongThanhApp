package com.congthanh.project.serviceImpl.ecommerce;

import com.congthanh.project.dto.ecommerce.ProductViewDTO;
import com.congthanh.project.entity.ecommerce.ProductView;
import com.congthanh.project.model.ecommerce.mapper.ProductViewMapper;
import com.congthanh.project.repository.productView.ProductViewRepository;
import com.congthanh.project.service.ecommerce.ProductViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ProductViewServiceImpl implements ProductViewService {

    private final ProductViewRepository productViewRepository;

    @Override
    public Long getTotalViewOfProduct(String productId) {
        return productViewRepository.getTotalViewOfProduct(productId);
    }

    @Override
    public ProductViewDTO addProductView(String productId, String customerId) {
        ProductView productView  = ProductView.builder()
                .productId(productId)
                .customerId(customerId)
                .viewedAt(Instant.now().toEpochMilli())
                .build();
        ProductView result = productViewRepository.save(productView);
        return ProductViewMapper.mapProductViewEntityToDTO(result);
    }
}
