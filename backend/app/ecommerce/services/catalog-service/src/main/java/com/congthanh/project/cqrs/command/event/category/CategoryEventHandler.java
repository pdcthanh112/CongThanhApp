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
        try {
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
            log.info("Lưu Category {} vào Postgres thành công, ID: {}", result.getName(), result.getId());
            CategoryQueueEvent<CategoryCreatedEvent> queueEvent = CategoryQueueEvent.<CategoryCreatedEvent>builder()
                    .eventType(CategoryEventType.CREATE)
                    .data(event)
                    .build();
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConstants.Category.ROUTING_KEY, queueEvent);
        } catch (Exception e) {
            log.error("Error sending event: ", e);
            throw e;
        }
    }
//@EventHandler
//    public void on(CategoryCreatedEvent event) {
//        try {
//            Category category = Category.builder()
//                    .id(event.getId())
//                    .name(event.getName())
//                    .slug(event.getSlug())
//                    .description(event.getDescription())
//                    .image(event.getImage())
//                    .parentId(null)
//                    .status(CategoryStatus.ACTIVE)
//                    .build();
//            var result = categoryRepository.save(category);
//            log.info("Lưu Category {} vào Postgres thành công, ID: {}", result.getName(), result.getId());
//            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConstants.Category.ROUTING_KEY, event);
//        } catch (Exception e) {
//            log.error("Error sending event: ", e);
//            throw e;
//        }
//    }

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
        log.info("Cập nhật Category {} vào Postgres thành công, ID: {}",
                result.getName(), result.getId());

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
        categoryRepository.save(category);

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConstants.Category.ROUTING_KEY, event);
    }

}
