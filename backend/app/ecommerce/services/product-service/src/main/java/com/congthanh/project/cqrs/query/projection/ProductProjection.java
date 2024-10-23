package com.congthanh.project.cqrs.query.projection;

import com.congthanh.project.cqrs.command.event.ProductCreatedEvent;
import com.congthanh.project.cqrs.query.query.GetAllProductQuery;
import com.congthanh.project.cqrs.query.query.GetProductByIdQuery;
import com.congthanh.project.cqrs.query.query.GetProductBySlugQuery;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.model.document.ProductDocument;
import com.congthanh.project.repository.product.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductProjection {

    private final ProductQueryRepository productQueryRepository;

    @QueryHandler
    public List<ProductDocument> getAllProduct(GetAllProductQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getLimit());
        List<ProductDocument> result = productQueryRepository.findAll(pageable).getContent();
        return result;
    }

    @QueryHandler
    public ProductDocument getProductById(GetProductByIdQuery product) {
        ProductDocument result = productQueryRepository.findById(product.getProductId()).orElseThrow(() -> new NotFoundException("Product not found"));
        return result;
    }

    @QueryHandler
    public ProductDocument getProductById(GetProductBySlugQuery product) {
        ProductDocument result = productQueryRepository.findById(product.getSlug()).orElseThrow(() -> new NotFoundException("Product not found"));
        return result;
    }

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductDocument productQuery = new ProductDocument();
        productQuery.setId(event.getId());
        productQuery.setName(event.getName());
        productQueryRepository.save(productQuery);
    }

}
