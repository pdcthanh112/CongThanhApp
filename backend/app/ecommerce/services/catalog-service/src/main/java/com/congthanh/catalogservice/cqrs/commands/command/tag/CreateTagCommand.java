package com.congthanh.catalogservice.cqrs.commands.command.tag;

import com.congthanh.catalogservice.constant.enums.TagStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class CreateTagCommand {

    @TargetAggregateIdentifier
    private Long id;

    private String name;

    private Instant createdAt;

    private String createdBy;

    private Instant updatedAt;

    private String updatedBy;

    private TagStatus status;

}
