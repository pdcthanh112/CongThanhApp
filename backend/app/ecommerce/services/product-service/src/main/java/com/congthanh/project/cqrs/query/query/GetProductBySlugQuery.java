package com.congthanh.project.cqrs.query.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetProductBySlugQuery {

    private String slug;

}
