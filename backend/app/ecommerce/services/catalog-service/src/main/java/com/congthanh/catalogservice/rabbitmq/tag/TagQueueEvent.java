package com.congthanh.catalogservice.rabbitmq.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagQueueEvent<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private TagEventType eventType;

    private T data;

}
