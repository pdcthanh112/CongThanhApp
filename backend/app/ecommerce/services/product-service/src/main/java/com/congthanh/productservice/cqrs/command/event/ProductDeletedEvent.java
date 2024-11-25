package com.congthanh.productservice.cqrs.command.event;

import lombok.Data;

@Data
public class ProductDeletedEvent {

    private String productId;

}
