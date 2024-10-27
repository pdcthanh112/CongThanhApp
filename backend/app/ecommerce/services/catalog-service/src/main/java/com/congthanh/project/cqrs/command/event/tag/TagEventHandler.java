package com.congthanh.project.cqrs.command.event.tag;

import com.congthanh.project.config.RabbitMQConfig;
import com.congthanh.project.constant.enums.TagStatus;
import com.congthanh.project.entity.Tag;
import com.congthanh.project.repository.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.Instant;

@ProcessingGroup("tag")
@RequiredArgsConstructor
@Slf4j
public class TagEventHandler {

    private final TagRepository tagRepository;

    private final RabbitTemplate rabbitTemplate;

    @EventHandler
    public void on(TagCreatedEvent event) {
        System.out.println("Teg event handler");
        Tag tag = Tag.builder()
                .id(event.getId())
                .name(event.getName())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .status(TagStatus.ACTIVE)
                .build();
        var result = tagRepository.save(tag);
        log.info("Lưu Tag {} vào Postgres thành công, ID: {}",
                result.getName(), result.getId());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, result);
    }
}
