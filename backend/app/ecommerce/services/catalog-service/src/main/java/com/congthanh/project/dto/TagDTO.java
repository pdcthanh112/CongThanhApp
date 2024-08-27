package com.congthanh.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDTO {

    private Long id;

    private String name;

    private Long createDate;

    private String status;

}
