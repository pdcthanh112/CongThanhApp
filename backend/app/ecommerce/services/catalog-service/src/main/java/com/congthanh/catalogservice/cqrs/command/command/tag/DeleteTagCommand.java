package com.congthanh.catalogservice.cqrs.command.command.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@Builder
public class DeleteTagCommand {

    @TargetAggregateIdentifier
    private long tagId;

}
