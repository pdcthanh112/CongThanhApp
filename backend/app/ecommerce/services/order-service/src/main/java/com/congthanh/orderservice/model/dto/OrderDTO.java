package com.congthanh.orderservice.model.dto;

import com.congthanh.orderservice.constant.enums.OrderStatus;
import com.congthanh.orderservice.constant.enums.PaymentStatus;
import com.congthanh.orderservice.constant.enums.ShippingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String customer;

    private Long shippingAddress;

    private Long billingAddress;

    private String note;

    private BigDecimal total;

    private Instant orderDate;

    private String checkout;

    @NotNull
    private OrderStatus status;

    private PaymentStatus paymentStatus;

    private ShippingStatus shippingStatus;

}
