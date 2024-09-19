package com.congthanh.project.cqrs.query.projection;

import com.congthanh.project.cqrs.command.event.ProductCreatedEvent;
import com.congthanh.project.model.document.ProductQuery;
import com.congthanh.project.repository.product.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductProjection {

    private final ProductQueryRepository productQueryRepository;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductQuery productQuery = new ProductQuery();
        productQuery.setId(event.getId());
        productQuery.setName(event.getName());
        productQueryRepository.save(productQuery);
    }

}
