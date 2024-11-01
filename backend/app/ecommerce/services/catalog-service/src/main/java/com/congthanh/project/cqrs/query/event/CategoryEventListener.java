package com.congthanh.project.cqrs.query.event;

import com.congthanh.project.config.RabbitMQConfig;
import com.congthanh.project.constant.common.RabbitMQConstants;
import com.congthanh.project.cqrs.command.event.category.CategoryCreatedEvent;
import com.congthanh.project.cqrs.command.event.category.CategoryDeletedEvent;
import com.congthanh.project.cqrs.command.event.category.CategoryUpdatedEvent;
import com.congthanh.project.model.document.CategoryDocument;
import com.congthanh.project.repository.category.CategoryDocumentRepository;
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

    //    @RabbitListener(queues = RabbitMQConstants.Category.QUEUE_NAME)
//    public void handleCategoryCreated(@Payload CategoryCreatedEvent event) {
//        System.out.println("Vào event listener");
//        CategoryDocument document = CategoryDocument.builder()
//                .id(event.getId())
//                .name(event.getName())
//                .slug(event.getSlug())
//                .description(event.getDescription())
//                .image(event.getImage())
//                .status(event.getStatus())
//                .build();
//        var result = categoryDocumentRepository.save(document);
//        log.info("Lưu Category {} vào Mongo thành công, ID: {}",
//                result.getName(), result.getId());
//    }
    @RabbitListener(queues = RabbitMQConstants.Category.QUEUE_NAME)
    public void handleCategoryCreated(CategoryCreatedEvent event) {
        try {
            log.info("Received event in listener: {}", event);
            CategoryDocument document = CategoryDocument.builder()
                    .id(event.getId())
                    .name(event.getName())
                    .slug(event.getSlug())
                    .description(event.getDescription())
                    .image(event.getImage())
                    .status(event.getStatus())
                    .build();
            var result = categoryDocumentRepository.save(document);
            log.info("Lưu Category {} vào Mongo thành công, ID: {}",
                    result.getName(), result.getId());
        } catch (Exception e) {
            log.error("Error processing event: ", e);
            throw e;
        }
    }

    @RabbitListener(queues = RabbitMQConstants.Category.QUEUE_NAME)
    public void handCategoryUpdated(@Payload CategoryUpdatedEvent event) {
        CategoryDocument document = CategoryDocument.builder()
                .id(event.getId())
                .name(event.getName())
                .slug(event.getSlug())
                .description(event.getDescription())
                .image(event.getImage())
                .status(event.getStatus())
                .build();
        var result = categoryDocumentRepository.save(document);
        log.info("Cập nhật Category {} vào Mongo thành công, ID: {}",
                result.getName(), result.getId());
    }

    @RabbitListener(queues = RabbitMQConstants.Category.QUEUE_NAME)
    public void handleCategoryDeleted(@Payload CategoryDeletedEvent event) {
        categoryDocumentRepository.deleteCategory(event.getId());
    }

}
