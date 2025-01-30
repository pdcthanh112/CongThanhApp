package com.congthanh.reviewservice.cqrs.command.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class CreateReviewCommand {

    @TargetAggregateIdentifier
    private Long id;
    private String content;
    private String author;
    private int rating;
    private String product;
    private String variant;
    private List<CreateReviewMediaCommand> reviewMedia;
    private Instant createdAt;
}
