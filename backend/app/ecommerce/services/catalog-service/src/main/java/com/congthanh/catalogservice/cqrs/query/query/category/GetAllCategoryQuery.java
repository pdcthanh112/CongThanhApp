package com.congthanh.catalogservice.cqrs.query.query.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAllCategoryQuery {
    private Integer page;
    private Integer size;
    private String sort;
    private String order;
    private String filter;
}
