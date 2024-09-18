package com.congthanh.project.cqrs.query.service;

import com.congthanh.project.cqrs.query.query.GetProductQuery;
import com.congthanh.project.model.document.ProductQuery;
import com.congthanh.project.repository.product.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductQueryService {

    private final ProductQueryRepository productQueryRepository;

    @QueryHandler
    public ProductQuery handle(GetProductQuery query) {
        return productQueryRepository.findByProductId(query.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

}
