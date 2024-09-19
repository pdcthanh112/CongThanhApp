package com.congthanh.project.cqrs.query.query;

import lombok.Data;

@Data
public class GetProductByIdQuery {

    private final String productId;

}
