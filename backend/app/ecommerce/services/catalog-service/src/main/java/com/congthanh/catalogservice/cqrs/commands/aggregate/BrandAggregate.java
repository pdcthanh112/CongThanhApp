package com.congthanh.catalogservice.cqrs.commands.aggregate;

import com.congthanh.catalogservice.cqrs.commands.command.brand.CreateBrandCommand;
import com.congthanh.catalogservice.cqrs.commands.event.brand.BrandCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class BrandAggregate {

    @AggregateIdentifier
    private Long id;
    private String name;
    private String slug;
    private String image;

    public BrandAggregate() {
    }

    @CommandHandler
    public BrandAggregate(CreateBrandCommand command) {
        apply(new BrandCreatedEvent(command.getId(), command.getName(), command.getSlug(), command.getImage()));
    }

    @EventSourcingHandler
    public void onBrandCreated(BrandCreatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.image = event.getImage();
    }
}
