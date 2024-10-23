package com.congthanh.project.cqrs.query.event;

import com.congthanh.project.config.RabbitMQConfig;
import com.congthanh.project.cqrs.command.event.category.CategoryCreatedEvent;
import com.congthanh.project.model.document.CategoryDocument;
import com.congthanh.project.repository.category.CategoryQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryEventListener {

    private final CategoryQueryRepository categoryQueryRepository;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleCategoryCreated(CategoryCreatedEvent event) {
        System.out.println("CategoryEventListener"+event);
        CategoryDocument document = CategoryDocument.builder()
                .id(event.getId())
                .name(event.getName())
                .slug(event.getSlug())
                .description(event.getDescription())
                .image(event.getImage())
                .status(event.getStatus())
                .build();
        categoryQueryRepository.save(document);
    }

}
