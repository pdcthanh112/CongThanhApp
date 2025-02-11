package com.congthanh.catalogservice.cqrs.commands.event.tag;

import com.congthanh.catalogservice.constant.common.RabbitMQConstants;
import com.congthanh.catalogservice.constant.enums.TagStatus;
import com.congthanh.catalogservice.model.document.TagDocument;
import com.congthanh.catalogservice.rabbitmq.tag.TagEventType;
import com.congthanh.catalogservice.rabbitmq.tag.TagQueueEvent;
import com.congthanh.catalogservice.repository.tag.TagDocumentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class TagEventListener {

    private final TagDocumentRepository tagDocumentRepository;

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConstants.Tag.QUEUE_NAME)
    public void handleTagCreated(@Payload TagQueueEvent<Object> event) {
        switch (event.getEventType()) {
            case TagEventType.CREATE -> {
                TagCreatedEvent createdEvent = objectMapper.convertValue(event.getData(), TagCreatedEvent.class);
                handleTagCreated(createdEvent);
            }
            case TagEventType.UPDATE -> {
                TagUpdatedEvent updatedEvent = objectMapper.convertValue(event.getData(), TagUpdatedEvent.class);
                handleTagUpdated(updatedEvent);
            }
            case TagEventType.DELETE -> {
                TagDeletedEvent deletedEvent = objectMapper.convertValue(event.getData(), TagDeletedEvent.class);
                handTagDeleted(deletedEvent);
            }
        }

    }

    private void handleTagCreated(TagCreatedEvent event) {
        TagDocument tag = TagDocument.builder()
                .id(event.getId())
                .name(event.getName())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .status(TagStatus.ACTIVE)
                .build();
        var result = tagDocumentRepository.save(tag);
        log.info("Save Tag {} into Mongo successfully, ID: {}", result.getName(), result.getId());
    }

    private void handleTagUpdated(TagUpdatedEvent event) {
        TagDocument tag = TagDocument.builder()
                .id(event.getId())
                .name(event.getName())
                .createdAt(event.getCreateAt())
                .updatedAt(event.getUpdateAt())
                .status(event.getStatus())
                .build();
        var result = tagDocumentRepository.save(tag);
        log.info("Update Tag {} into Mongo successfully, ID: {}", result.getName(), result.getId());
    }

    private void handTagDeleted(TagDeletedEvent event) {
        tagDocumentRepository.deleteTagDocument(event.getTagId());
    }

}
