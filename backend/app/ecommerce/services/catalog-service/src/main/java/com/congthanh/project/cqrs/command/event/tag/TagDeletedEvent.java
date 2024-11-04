package com.congthanh.project.cqrs.command.event.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class TagDeletedEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long tagId;

}
