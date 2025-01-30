package com.congthanh.reviewservice.cqrs.command.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateReviewMediaCommand {

    private Long id;

    private String url;

}
