package com.congthanh.project.cqrs.command.service;

import com.congthanh.project.cqrs.command.event.ProductCreatedEvent;
import com.congthanh.project.model.document.ProductQuery;
import com.congthanh.project.repository.product.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductEventHandler {

    private final ProductQueryRepository productQueryRepository;

    @EventListener
    @RabbitListener(queues = "productQueue")
    public void handleProductCreatedEvent(ProductCreatedEvent event) {
        ProductQuery product = ProductQuery.builder()
                .id(event.getId())
                .name(event.getName())
                .category(event.getCategory())
                .subcategory(event.getSubcategory())
                .slug(event.getSlug())
                .description(event.getDescription())
                .brand(event.getBrand())
                .build();

        productQueryRepository.save(product);
    }

}
