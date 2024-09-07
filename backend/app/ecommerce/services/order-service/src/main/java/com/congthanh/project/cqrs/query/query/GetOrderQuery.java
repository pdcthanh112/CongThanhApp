package com.congthanh.project.cqrs.query.query;

import lombok.Data;

@Data
public class GetOrderQuery {

    private final String orderId;

}
