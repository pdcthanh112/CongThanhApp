package com.congthanh.project.cqrs.command.event.category;

import com.congthanh.project.entity.Category;
import com.congthanh.project.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryEventHandler {

    private final CategoryRepository categoryRepository;

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
        categoryRepository.save(category);
    }

}
