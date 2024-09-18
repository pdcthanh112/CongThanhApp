package com.congthanh.project.cqrs.query.projection;

import com.congthanh.project.cqrs.command.event.ProductCreatedEvent;
import com.congthanh.project.cqrs.query.query.GetProductQuery;
import com.congthanh.project.dto.ProductDTO;
import com.congthanh.project.entity.Product;
import com.congthanh.project.model.document.ProductQuery;
import com.congthanh.project.repository.product.ProductQueryRepository;
import com.congthanh.project.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductProjection {

    private final ProductQueryRepository productQueryRepository;

//    @QueryHandler
//    public List<ProductDTO> handle(GetProductQuery getProductsQuery) {
//        List<Product> products = productQueryRepository.findAll();
//
//        List<ProductDTO> result =
//                products.stream()
//                        .map(product -> ProductDTO
//                                .builder()
//                                .id(product.getId())
//                                .name(product.getName())
//                                .slug(product.getSlug())
//                                .build())
//                        .collect(Collectors.toList());
//
//        return result;
//    }

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductQuery productQuery = new ProductQuery();
        productQuery.setProductId(event.getId());
        productQuery.setName(event.getName());
        productQueryRepository.save(productQuery);
    }

}
