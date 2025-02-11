package com.congthanh.catalogservice.cqrs.queries.query.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetCategoryByIdQuery {

    private String id;

}
