package com.congthanh.project.cqrs.command.event.category;

import com.congthanh.project.constant.common.RabbitMQConstants;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.model.document.CategoryDocument;
import com.congthanh.project.model.document.SubcategoryDocument;
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
            case CategoryEventType.ADD_SUBCATEGORY -> {
                SubcategoryAddedEvent subcategoryAddedEvent = objectMapper.convertValue(event.getData(), SubcategoryAddedEvent.class);
                handleAddSubcategory(subcategoryAddedEvent);
            }
            case CategoryEventType.REMOVE_SUBCATEGORY -> {
                SubcategoryRemovedEvent removedEvent = objectMapper.convertValue(event.getData(), SubcategoryRemovedEvent.class);
                handleRemoveSubcategory(removedEvent);
            }
        }
    }

    private void handleCategoryCreated(CategoryCreatedEvent event) {
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
        log.info("Save Category {} into Mongo successfully, ID: {}", result.getName(), result.getId());
    }

    private void handCategoryUpdated(CategoryUpdatedEvent event) {
        CategoryDocument category = CategoryDocument.builder()
                .id(event.getId())
                .name(event.getName())
                .slug(event.getSlug())
                .description(event.getDescription())
                .image(event.getImage())
                .status(event.getStatus())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .build();
        var result = categoryDocumentRepository.save(category);
        log.info("Update Category {} into Mongo successfully, ID: {}", result.getName(), result.getId());
    }

    private void handleCategoryDeleted(CategoryDeletedEvent event) {
        categoryDocumentRepository.deleteCategory(event.getId());
    }

    private void handleAddSubcategory(SubcategoryAddedEvent event) {
        SubcategoryDocument subcategoryDocument = SubcategoryDocument.builder()
                .id(event.getId())
                .name(event.getName())
                .slug(event.getSlug())
                .description(event.getDescription())
                .image(event.getImage())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .status(event.getStatus())
                .build();
        var result = categoryDocumentRepository.addSubcategory(subcategoryDocument, event.getParentId());
        log.info("Add SubCategory {} into Mongo successfully, ID: {}", result.getName(), result.getId());
    }

    private void handleRemoveSubcategory(SubcategoryRemovedEvent event) {
        CategoryDocument category = categoryDocumentRepository.findById(event.getParentId())
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + event.getId()));

        // Kiểm tra subcategory có tồn tại trong category
        boolean subcategoryExists = category.getSubcategories() != null &&
                category.getSubcategories().stream()
                        .anyMatch(sub -> sub.getId().equals(event.getId()));

        if (!subcategoryExists) {
            throw new NotFoundException("Subcategory not found with id: " + event.getId());
        }

        var result = categoryDocumentRepository.removeSubcategory(event.getParentId(), event.getId());
        log.info("Remove SubCategory {} in Mongo successfully, ID: {}", result.getName(), result.getId());
    }

}
