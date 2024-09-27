package com.congthanh.project.cqrs.command.event;

import lombok.Data;

@Data
public class ProductDeletedEvent {

    private String productId;

}
