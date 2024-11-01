package com.congthanh.project.cqrs.query.event;

import com.congthanh.project.config.RabbitMQConfig;
import com.congthanh.project.constant.common.RabbitMQConstants;
import com.congthanh.project.constant.enums.TagStatus;
import com.congthanh.project.cqrs.command.event.tag.TagCreatedEvent;
import com.congthanh.project.model.document.TagDocument;
import com.congthanh.project.repository.tag.TagDocumentRepository;
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

//    @RabbitListener(queues = RabbitMQConstants.Tag.QUEUE_NAME)
//    public void handleTagCreated(@Payload TagCreatedEvent event) {
//        System.out.println("Tag event listener");
//        TagDocument tag = TagDocument.builder()
//                .id(event.getId())
//                .name(event.getName())
//                .createdAt(Instant.now())
//                .updatedAt(Instant.now())
//                .status(TagStatus.ACTIVE)
//                .build();
//        var result = tagDocumentRepository.save(tag);
//        log.info("Lưu Tag {} vào Mongo thành công, ID: {}",
//                result.getName(), result.getId());
//    }

}
