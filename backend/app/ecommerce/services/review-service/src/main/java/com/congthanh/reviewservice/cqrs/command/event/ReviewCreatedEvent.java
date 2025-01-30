package com.congthanh.reviewservice.cqrs.command.event;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@Builder
public class ReviewCreatedEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String content;
    private String author;
    private int rating;
    private String product;
    private String variant;
    private List<ReviewMediaCreatedEvent> reviewMedia;
    private Instant createdAt;
}
