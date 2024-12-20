package com.congthanh.catalogservice.cqrs.command.event.tag;

import com.congthanh.catalogservice.config.RabbitMQConfig;
import com.congthanh.catalogservice.constant.common.RabbitMQConstants;
import com.congthanh.catalogservice.model.entity.Tag;
import com.congthanh.catalogservice.rabbitmq.tag.TagEventType;
import com.congthanh.catalogservice.rabbitmq.tag.TagQueueEvent;
import com.congthanh.catalogservice.repository.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("tag")
@RequiredArgsConstructor
@Slf4j
public class TagEventHandler {

    private final TagRepository tagRepository;

    private final RabbitTemplate rabbitTemplate;

    @EventHandler
    public void on(TagCreatedEvent event) {
        System.out.println("tag event handler");
        Tag tag = Tag.builder()
                .id(event.getId())
                .name(event.getName())
//                .createdAt(event.getCreateAt())
//                .updatedAt(event.getUpdateAt())
                .status(event.getStatus())
                .build();
        var result = tagRepository.save(tag);

        log.info("Save Tag {} into Postgres successfully, ID: {}", result.getName(), result.getId());
        TagQueueEvent<TagCreatedEvent> queueEvent = TagQueueEvent.<TagCreatedEvent>builder()
                .eventType(TagEventType.CREATE)
                .data(event)
                .build();
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConstants.Tag.ROUTING_KEY, queueEvent);
    }

    @EventHandler
    public void on(TagUpdatedEvent event) {
        System.out.println("tag event handler");
        Tag tag = Tag.builder()
                .id(event.getId())
                .name(event.getName())
//                .createdAt(event.getCreateAt())
//                .updatedAt(event.getUpdateAt())
                .status(event.getStatus())
                .build();
        var result = tagRepository.save(tag);
        log.info("Update Tag {} into Postgres successfully, ID: {}", result.getName(), result.getId());
        TagQueueEvent<TagUpdatedEvent> queueEvent = TagQueueEvent.<TagUpdatedEvent>builder()
                .eventType(TagEventType.UPDATE)
                .data(event)
                .build();
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConstants.Tag.ROUTING_KEY, queueEvent);
    }

    @ResetHandler
    public void reset() {
        tagRepository.deleteAllInBatch();
    }
}
