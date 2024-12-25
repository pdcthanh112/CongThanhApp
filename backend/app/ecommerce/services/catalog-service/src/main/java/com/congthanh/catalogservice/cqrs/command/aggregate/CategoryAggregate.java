package com.congthanh.catalogservice.cqrs.command.aggregate;

import com.congthanh.catalogservice.constant.enums.CategoryStatus;
import com.congthanh.catalogservice.cqrs.command.command.category.*;
import com.congthanh.catalogservice.cqrs.command.event.category.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class CategoryAggregate {

    @AggregateIdentifier
    private String id;
    private String name;
    private String slug;
    private String description;
    private String image;
    private String parentId;
    private CategoryStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    public CategoryAggregate() {
    }

    @CommandHandler
    public CategoryAggregate(CreateCategoryCommand command) {
        apply(CategoryCreatedEvent.builder()
                .id(command.getId())
                .name(command.getName())
                .slug(command.getSlug())
                .description(command.getDescription())
                .image(command.getImage())
                .status(command.getStatus())
                .createdAt(command.getCreatedAt())
                .createdBy(command.getCreatedBy())
                .updatedAt(command.getUpdatedAt())
                .updatedBy(command.getUpdatedBy())
                .build());
    }

    @EventSourcingHandler
    public void onCategoryCreated(CategoryCreatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.slug = event.getSlug();
        this.description = event.getDescription();
        this.image = event.getImage();
        this.parentId = event.getParentId();
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handleUpdateCategory(UpdateCategoryCommand command) {
        apply(new CategoryUpdatedEvent(command.getId(), command.getName(), command.getSlug(), command.getDescription(), command.getImage(), command.getParentId(), command.getCreatedAt(), command.getUpdatedAt(), command.getStatus()));
    }

    @EventSourcingHandler
    public void onCategoryUpdated(CategoryUpdatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.slug = event.getSlug();
        this.description = event.getDescription();
        this.image = event.getImage();
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handleDeleteCategory(DeleteCategoryCommand command) {
        apply(CategoryDeletedEvent.builder()
                .id(command.getCategoryId())
                .build());
//        apply(new CategoryDeletedEvent(command.getCategoryId()));
    }

    @EventSourcingHandler
    public void onCategoryDeleted(CategoryDeletedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.slug = event.getSlug();
        this.description = event.getDescription();
        this.image = event.getImage();
    }

    @CommandHandler
    public void handleAddSubcategory(AddSubcategoryCommand command) {
        apply(new SubcategoryAddedEvent(command.getId(), command.getName(), command.getSlug(), command.getDescription(), command.getImage(), command.getParentId(), command.getCreatedAt(), command.getUpdatedAt(), command.getStatus()));
    }

    @EventSourcingHandler
    public void onSubcategoryAdded(SubcategoryAddedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.slug = event.getSlug();
        this.description = event.getDescription();
        this.image = event.getImage();
    }

    @CommandHandler
    public void handleRemoveSubcategory(RemoveSubcategoryCommand command) {
        apply(SubcategoryRemovedEvent.builder()
                .id(command.getCategoryId())
                .parentId(command.getParentId())
                .build());
    }

    @EventSourcingHandler
    public void onSubcategoryRemoved(SubcategoryRemovedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.slug = event.getSlug();
        this.description = event.getDescription();
        this.image = event.getImage();
        this.parentId = event.getParentId();
        this.createdAt = event.getCreatedAt();
        this.updatedAt = event.getUpdatedAt();
        this.status = event.getStatus();
    }

}
