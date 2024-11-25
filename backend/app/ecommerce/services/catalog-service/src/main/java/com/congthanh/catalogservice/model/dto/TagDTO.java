package com.congthanh.catalogservice.model.dto;

import com.congthanh.catalogservice.constant.enums.TagStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDTO {

    private Long id;

    private String name;

    private Instant createAt;

    private Instant updateAt;

    private TagStatus status;

}
