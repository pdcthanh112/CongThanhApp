package com.congthanh.productservice.cqrs.query.projection;

import com.congthanh.productservice.cqrs.query.query.GetAllProductQuery;
import com.congthanh.productservice.cqrs.query.query.GetProductByIdQuery;
import com.congthanh.productservice.cqrs.query.query.GetProductBySlugQuery;
import com.congthanh.productservice.cqrs.query.query.GetProductDetailBySlugQuery;
import com.congthanh.productservice.exception.ecommerce.NotFoundException;
import com.congthanh.productservice.model.document.ProductDocument;
import com.congthanh.productservice.repository.product.ProductDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductProjection {

    private final ProductDocumentRepository productDocumentRepository;

    @QueryHandler
    public List<ProductDocument> getAllProduct(GetAllProductQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getLimit());
        List<ProductDocument> result = productDocumentRepository.findAll(pageable).getContent();
        return result;
    }

    @QueryHandler
    public ProductDocument getProductById(GetProductByIdQuery query) {
        ProductDocument result = productDocumentRepository.findById(query.getProductId()).orElseThrow(() -> new NotFoundException("Product not found"));
        return result;
    }

    @QueryHandler
    public ProductDocument getProductBySlug(GetProductBySlugQuery query) {
        ProductDocument result = productDocumentRepository.findBySlug(query.getSlug()).orElseThrow(() -> new NotFoundException("Product not found"));
        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRR"+result);
        return result;
    }

    @QueryHandler
    public ProductDocument getProductDetailBySlug(GetProductDetailBySlugQuery query) {
        ProductDocument result = productDocumentRepository.findBySlug(query.getSlug()).orElseThrow(() -> new NotFoundException("Product not found"));
        if(result.isHasVariant()) {
        }
        return result;
    }

}
