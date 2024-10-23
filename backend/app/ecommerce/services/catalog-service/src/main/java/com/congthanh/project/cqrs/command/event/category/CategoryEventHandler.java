package com.congthanh.project.cqrs.command.event.category;

import com.congthanh.project.config.RabbitMQConfig;
import com.congthanh.project.entity.Category;
import com.congthanh.project.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@ProcessingGroup("category")
@Component
@RequiredArgsConstructor
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
                .status(event.getStatus())
                .build();
        var result = categoryRepository.save(category);

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, event);
//        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, result);
    }

}
