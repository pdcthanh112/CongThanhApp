package com.congthanh.project.cqrs.command.command.tag;

import com.congthanh.project.constant.enums.TagStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class UpdateTagCommand {

    @TargetAggregateIdentifier
    private Long id;

    private String name;

    private Instant createAt;

    private Instant updateAt;

    private TagStatus status;

}