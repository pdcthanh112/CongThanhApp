package com.congthanh.catalogservice.cqrs.commands.event.tag;

import com.congthanh.catalogservice.constant.enums.TagStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class TagCreatedEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Instant createAt;

    private String createdBy;

    private Instant updateAt;

    private String updatedBy;

    private TagStatus status;

}
