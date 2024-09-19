package com.congthanh.project.cqrs.query.query;

import lombok.Data;

@Data
public class GetAllProductQuery {

    private final Integer page;

    private final Integer limit;

}
