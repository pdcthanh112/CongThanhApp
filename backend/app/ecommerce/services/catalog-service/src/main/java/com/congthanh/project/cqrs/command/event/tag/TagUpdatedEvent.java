package com.congthanh.project.cqrs.command.event.tag;

import com.congthanh.project.constant.enums.TagStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class TagUpdatedEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Instant createAt;

    private Instant updateAt;

    private TagStatus status;

}
