package com.congthanh.productservice.service.serviceImpl;

import com.congthanh.productservice.model.dto.ProductViewDTO;
import com.congthanh.productservice.model.entity.ProductView;
import com.congthanh.productservice.model.mapper.ProductViewMapper;
import com.congthanh.productservice.repository.productView.ProductViewRepository;
import com.congthanh.productservice.service.ProductViewService;
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
        ProductView productView = ProductView.builder()
                .productId(productId)
                .viewBy(customerId)
                .viewedAt(Instant.now())
                .build();
        ProductView result = productViewRepository.save(productView);
        return ProductViewMapper.mapProductViewEntityToDTO(result);
    }
}
