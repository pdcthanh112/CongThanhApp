package com.congthanh.catalogservice.cqrs.command.aggregate;

import com.congthanh.catalogservice.constant.enums.TagStatus;
import com.congthanh.catalogservice.cqrs.command.command.tag.CreateTagCommand;
import com.congthanh.catalogservice.cqrs.command.command.tag.UpdateTagCommand;
import com.congthanh.catalogservice.cqrs.command.event.tag.TagCreatedEvent;
import com.congthanh.catalogservice.cqrs.command.event.tag.TagUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class TagAggregate {

    @AggregateIdentifier
    private Long id;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;
    private TagStatus status;

    public TagAggregate() {
    }

    @CommandHandler
    public TagAggregate(CreateTagCommand command) {
        apply(new TagCreatedEvent(command.getId(), command.getName(), command.getCreatedAt(), command.getUpdatedAt(), command.getStatus()));
    }

    @EventSourcingHandler
    public void onTagCreated(TagCreatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.createdAt = event.getCreateAt();
        this.updatedAt = event.getUpdateAt();
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handleUpdateTag(UpdateTagCommand command) {
        apply(new TagUpdatedEvent(command.getId(), command.getName(), command.getCreateAt(), command.getUpdateAt(), command.getStatus()));
    }

    @EventSourcingHandler
    public void onTagUpdated(TagUpdatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.createdAt = event.getCreateAt();
        this.updatedAt = event.getUpdateAt();
        this.status = event.getStatus();
    }
}
