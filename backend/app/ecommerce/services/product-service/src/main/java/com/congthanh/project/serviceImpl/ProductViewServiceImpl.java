package com.congthanh.project.serviceImpl;

import com.congthanh.project.dto.ProductViewDTO;
import com.congthanh.project.entity.ProductView;
import com.congthanh.project.model.ecommerce.mapper.ProductViewMapper;
import com.congthanh.project.repository.productView.ProductViewRepository;
import com.congthanh.project.service.ProductViewService;
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
