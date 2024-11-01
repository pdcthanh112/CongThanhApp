package com.congthanh.project.rabbitmq.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryQueueEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private EventType eventType;

    private Object data;

}
