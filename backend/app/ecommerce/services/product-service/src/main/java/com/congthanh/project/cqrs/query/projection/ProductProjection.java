package com.congthanh.project.cqrs.query.projection;

import com.congthanh.project.cqrs.query.query.GetProductQuery;
import com.congthanh.project.dto.ProductDTO;
import com.congthanh.project.entity.Product;
import com.congthanh.project.repository.product.ProductRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductProjection {

    private ProductRepository productRepository;

    @QueryHandler
    public List<ProductDTO> handle(GetProductQuery getProductsQuery) {
        List<Product> products = productRepository.findAll();

        List<ProductDTO> result =
                products.stream()
                        .map(product -> ProductDTO
                                .builder()
                                .id(product.getId())
                                .name(product.getName())
                                .slug(product.getSlug())
                                .build())
                        .collect(Collectors.toList());

        return result;
    }

}
