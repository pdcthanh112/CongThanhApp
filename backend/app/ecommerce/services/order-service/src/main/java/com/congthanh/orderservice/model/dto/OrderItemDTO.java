package com.congthanh.orderservice.model.dto;

import com.congthanh.orderservice.constant.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
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
public class OrderItemDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private int quantity;

    private String productId;

    private String order;

    private OrderStatus status;

}
