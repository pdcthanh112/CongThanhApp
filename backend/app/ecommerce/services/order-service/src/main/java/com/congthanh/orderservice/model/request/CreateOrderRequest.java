package com.congthanh.orderservice.model.request;

import com.congthanh.orderservice.model.entity.OrderItem;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {

    @NotNull
    private String customer;

    private BigDecimal total;

    private String promotionCode;

    private List<OrderItem> orderItems;

}
