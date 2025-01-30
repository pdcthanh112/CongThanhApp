package com.congthanh.reviewservice.cqrs.command.aggregate;

import com.congthanh.reviewservice.cqrs.command.command.CreateReviewCommand;
import com.congthanh.reviewservice.cqrs.command.event.ReviewCreatedEvent;
import com.congthanh.reviewservice.cqrs.command.event.ReviewMediaCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class ReviewAggregate {

    @AggregateIdentifier
    private Long id;
    private String content;
    private String author;
    private int rating;
    private String product;
    private String variant;
    private List<?> reviewMedia;
    private Instant createdAt;

    public ReviewAggregate() {
    }

    @CommandHandler
    public ReviewAggregate(CreateReviewCommand command) {
        apply(ReviewCreatedEvent.builder()
                .id(command.getId())
                .content(command.getContent())
                .author(command.getAuthor())
                .rating(command.getRating())
                .product(command.getProduct())
                .variant(command.getVariant())
                .reviewMedia(command.getReviewMedia().stream()
                        .map(item -> ReviewMediaCreatedEvent.builder()
                                .id(item.getId())
                                .url(item.getUrl())
                                .build())
                        .collect(Collectors.toList()))
                .createdAt(command.getCreatedAt())
                .build());
    }

    public void on(ReviewCreatedEvent event) {
        this.id = event.getId();
        this.content = event.getContent();
        this.author = event.getAuthor();
        this.rating = event.getRating();
        this.product = event.getProduct();
        this.variant = event.getVariant();
        this.reviewMedia = event.getReviewMedia();
        this.createdAt = event.getCreatedAt();
    }

}
