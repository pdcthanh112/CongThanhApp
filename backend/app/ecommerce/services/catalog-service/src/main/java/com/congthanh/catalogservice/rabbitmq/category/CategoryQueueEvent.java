package com.congthanh.catalogservice.rabbitmq.category;

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
public class CategoryQueueEvent<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private CategoryEventType eventType;

    private T data;
}
