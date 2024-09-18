package com.congthanh.project.cqrs.query.query;

import lombok.Data;

@Data
public class GetProductQuery {

    private final String productId;

}
