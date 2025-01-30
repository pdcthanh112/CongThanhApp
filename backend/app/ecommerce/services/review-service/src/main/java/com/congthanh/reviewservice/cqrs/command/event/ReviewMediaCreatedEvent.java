package com.congthanh.reviewservice.cqrs.command.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewMediaCreatedEvent {

    private Long id;

    private String url;

}
