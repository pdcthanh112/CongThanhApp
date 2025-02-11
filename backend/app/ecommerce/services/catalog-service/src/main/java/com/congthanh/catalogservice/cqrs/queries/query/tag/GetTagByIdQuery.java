package com.congthanh.catalogservice.cqrs.queries.query.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetTagByIdQuery {

    private Long id;

}
