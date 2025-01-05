package com.congthanh.customerservice.cqrs.command.command.shippingAddress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@Builder
public class DeleteShippingAddressCommand {

    @TargetAggregateIdentifier
    private Long id;

}
