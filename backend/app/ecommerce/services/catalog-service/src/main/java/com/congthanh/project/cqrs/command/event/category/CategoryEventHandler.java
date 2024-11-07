package com.congthanh.project.cqrs.command.event.category;

import com.congthanh.project.config.RabbitMQConfig;
import com.congthanh.project.constant.common.RabbitMQConstants;
import com.congthanh.project.constant.enums.CategoryStatus;
import com.congthanh.project.entity.Category;
import com.congthanh.project.rabbitmq.category.CategoryEventType;
import com.congthanh.project.rabbitmq.category.CategoryQueueEvent;
import com.congthanh.project.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("category")
@RequiredArgsConstructor
@Slf4j
public class CategoryEventHandler {

    private final CategoryRepository categoryRepository;

    private final RabbitTemplate rabbitTemplate;

    @EventHandler
    public void on(CategoryCreatedEvent event) {
        Category category = Category.builder()
                .id(event.getId())
                .name(event.getName())
                .slug(event.getSlug())
                .description(event.getDescription())
                .image(event.getImage())
                .parentId(null)
                .status(CategoryStatus.ACTIVE)
                .build();
        var result = categoryRepository.save(category);
        log.info("Save Category {} into Postgres successfully, ID: {}", result.getName(), result.getId());
        CategoryQueueEvent<CategoryCreatedEvent> queueEvent = CategoryQueueEvent.<CategoryCreatedEvent>builder()
                .eventType(CategoryEventType.CREATE)
                .data(event)
                .build();
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConstants.Category.ROUTING_KEY, queueEvent);

    }

    @EventHandler
    public void on(CategoryUpdatedEvent event) {
        Category category = Category.builder()
                .id(event.getId())
                .name(event.getName())
                .slug(event.getSlug())
                .description(event.getDescription())
                .image(event.getImage())
                .parentId(event.getParentId())
                .status(event.getStatus())
                .build();
        var result = categoryRepository.save(category);
        log.info("Update Category {} into Postgres successfully, ID: {}", result.getName(), result.getId());
        CategoryQueueEvent<CategoryUpdatedEvent> queueEvent = CategoryQueueEvent.<CategoryUpdatedEvent>builder()
                .eventType(CategoryEventType.UPDATE)
                .data(event)
                .build();
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConstants.Category.ROUTING_KEY, queueEvent);
    }

    @EventHandler
    public void on(CategoryDeletedEvent event) {
        Category category = Category.builder()
                .id(event.getId())
                .name(event.getName())
                .slug(event.getSlug())
                .description(event.getDescription())
                .image(event.getImage())
                .parentId(null)
                .status(CategoryStatus.INACTIVE)
                .build();
        var result = categoryRepository.save(category);
        log.info("Delete Category {} in Postgres successfully, ID: {}", result.getName(), result.getId());
        CategoryQueueEvent<CategoryDeletedEvent> queueEvent = CategoryQueueEvent.<CategoryDeletedEvent>builder()
                .eventType(CategoryEventType.DELETE)
                .data(event)
                .build();
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConstants.Category.ROUTING_KEY, queueEvent);
    }

    @EventHandler
    public void on(SubcategoryAddedEvent event) {
        Category category = Category.builder()
                .id(event.getId())
                .name(event.getName())
                .slug(event.getSlug())
                .description(event.getDescription())
                .image(event.getImage())
                .parentId(event.getParentId())
                .status(CategoryStatus.ACTIVE)
                .build();
        var result = categoryRepository.save(category);
        log.info("Add SubCategory {} with ID: {} into Postgres successfully, for parent: {}", result.getName(), result.getId(), result.getParentId());
        CategoryQueueEvent<SubcategoryAddedEvent> queueEvent = CategoryQueueEvent.<SubcategoryAddedEvent>builder()
                .eventType(CategoryEventType.ADD_SUBCATEGORY)
                .data(event)
                .build();
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConstants.Category.ROUTING_KEY, queueEvent);
    }

    @EventHandler
    public void on(SubcategoryRemovedEvent event) {
        Category category = Category.builder()
                .id(event.getId())
                .name(event.getName())
                .slug(event.getSlug())
                .description(event.getDescription())
                .image(event.getImage())
                .parentId(event.getParentId())
                .status(CategoryStatus.ACTIVE)
                .build();
        var result = categoryRepository.save(category);
        log.info("Remove SubCategory {} with ID: {} into Postgres successfully, for parent: {}", result.getName(), result.getId(), result.getParentId());
        CategoryQueueEvent<SubcategoryRemovedEvent> queueEvent = CategoryQueueEvent.<SubcategoryRemovedEvent>builder()
                .eventType(CategoryEventType.REMOVE_SUBCATEGORY)
                .data(event)
                .build();
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConstants.Category.ROUTING_KEY, queueEvent);
    }

    @ResetHandler
    public void reset() {
        categoryRepository.deleteAllInBatch();
    }

}
