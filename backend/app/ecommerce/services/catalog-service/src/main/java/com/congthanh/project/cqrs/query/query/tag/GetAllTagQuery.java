package com.congthanh.project.cqrs.query.query.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAllTagQuery {

    private Integer page;

    private Integer limit;

    private String sortBy;

    private String sortDirection;

}
