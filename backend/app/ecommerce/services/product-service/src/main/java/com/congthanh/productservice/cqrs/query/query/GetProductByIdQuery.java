package com.congthanh.productservice.cqrs.query.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetProductByIdQuery {

    private String productId;

}
