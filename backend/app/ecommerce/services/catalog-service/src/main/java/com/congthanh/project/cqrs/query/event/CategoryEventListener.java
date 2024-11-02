package com.congthanh.project.cqrs.query.event;

import com.congthanh.project.constant.common.RabbitMQConstants;
import com.congthanh.project.cqrs.command.event.category.CategoryCreatedEvent;
import com.congthanh.project.cqrs.command.event.category.CategoryDeletedEvent;
import com.congthanh.project.cqrs.command.event.category.CategoryUpdatedEvent;
import com.congthanh.project.model.document.CategoryDocument;
import com.congthanh.project.rabbitmq.category.CategoryEventType;
import com.congthanh.project.rabbitmq.category.CategoryQueueEvent;
import com.congthanh.project.repository.category.CategoryDocumentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CategoryEventListener {

    private final CategoryDocumentRepository categoryDocumentRepository;

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConstants.Category.QUEUE_NAME)
    public void handleCategoryEvent(@Payload CategoryQueueEvent<Object> event) {
        switch (event.getEventType()) {
            case CategoryEventType.CREATE -> {
                CategoryCreatedEvent createdEvent = objectMapper.convertValue(event.getData(), CategoryCreatedEvent.class);
                handleCategoryCreated(createdEvent);
            }
            case CategoryEventType.UPDATE -> {
                CategoryUpdatedEvent updatedEvent = objectMapper.convertValue(event.getData(), CategoryUpdatedEvent.class);
                handCategoryUpdated(updatedEvent);
            }
            case CategoryEventType.DELETE -> {
                CategoryDeletedEvent deletedEvent = objectMapper.convertValue(event.getData(), CategoryDeletedEvent.class);
                handleCategoryDeleted(deletedEvent);
            }
        }
    }

    private void handleCategoryCreated(CategoryCreatedEvent event) {
        try {
            CategoryDocument document = CategoryDocument.builder()
                    .id(event.getId())
                    .name(event.getName())
                    .slug(event.getSlug())
                    .description(event.getDescription())
                    .image(event.getImage())
                    .status(event.getStatus())
                    .createdAt(event.getCreatedAt())
                    .updatedAt(event.getUpdatedAt())
                    .build();
            var result = categoryDocumentRepository.save(document);
            log.info("Lưu Category {} vào Mongo thành công, ID: {}",
                    result.getName(), result.getId());
        } catch (Exception e) {
            log.error("Error processing event: ", e);
            throw e;
        }
    }

    private void handCategoryUpdated(CategoryUpdatedEvent event) {
        CategoryDocument document = CategoryDocument.builder()
                .id(event.getId())
                .name(event.getName())
                .slug(event.getSlug())
                .description(event.getDescription())
                .image(event.getImage())
                .status(event.getStatus())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .build();
        var result = categoryDocumentRepository.save(document);
        log.info("Cập nhật Category {} vào Mongo thành công, ID: {}",
                result.getName(), result.getId());
    }

    private void handleCategoryDeleted(CategoryDeletedEvent event) {
        categoryDocumentRepository.deleteCategory(event.getId());
    }

}
