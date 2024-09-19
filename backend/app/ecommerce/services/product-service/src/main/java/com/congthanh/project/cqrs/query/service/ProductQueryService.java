package com.congthanh.project.cqrs.query.service;

import com.congthanh.project.cqrs.query.query.GetAllProductQuery;
import com.congthanh.project.cqrs.query.query.GetProductByIdQuery;
import com.congthanh.project.model.document.ProductQuery;
import com.congthanh.project.repository.product.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductQueryService {

    private final ProductQueryRepository productQueryRepository;

    @QueryHandler
    public ProductQuery handle(GetProductByIdQuery query) {
        return productQueryRepository.findByProductId(query.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @QueryHandler
    public List<ProductQuery> handle(GetAllProductQuery query) {
        List<ProductQuery> result = productQueryRepository.findAll();
        return result.stream()
                .map(product -> ProductQuery
                        .builder()
                        .id(product.getId())
                        .name(product.getName())
                        .slug(product.getSlug())
                        .build())
                .collect(Collectors.toList());
    }

}
