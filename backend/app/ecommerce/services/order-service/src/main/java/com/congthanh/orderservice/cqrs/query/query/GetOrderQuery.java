package com.congthanh.orderservice.cqrs.query.query;

import lombok.Data;

@Data
public class GetOrderQuery {

    private final String orderId;

}
