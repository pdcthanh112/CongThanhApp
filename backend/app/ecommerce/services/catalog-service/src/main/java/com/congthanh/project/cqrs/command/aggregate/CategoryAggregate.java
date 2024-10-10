package com.congthanh.project.cqrs.command.aggregate;

import com.congthanh.project.cqrs.command.command.category.CreateCategoryCommand;
import com.congthanh.project.cqrs.command.command.category.UpdateCategoryCommand;
import com.congthanh.project.cqrs.command.event.category.CategoryCreatedEvent;
import com.congthanh.project.cqrs.command.event.category.CategoryUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class CategoryAggregate {

    @AggregateIdentifier
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String image;
    private String status;

    public CategoryAggregate() {
    }

    @CommandHandler
    public void handleCreateCategory(CreateCategoryCommand command) {
        apply(new CategoryCreatedEvent(command.getId(), command.getName(), command.getSlug(), command.getDescription(), command.getImage(), command.getStatus()));
    }

    @EventSourcingHandler
    public void onCategoryCreated(CategoryCreatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.slug = event.getSlug();
        this.description = event.getDescription();
        this.image = event.getImage();
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handleUpdateCategory(UpdateCategoryCommand command) {
        apply(new CategoryUpdatedEvent(command.getId(), command.getName(), command.getSlug(), command.getDescription(), command.getImage(), command.getStatus()));
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

}
