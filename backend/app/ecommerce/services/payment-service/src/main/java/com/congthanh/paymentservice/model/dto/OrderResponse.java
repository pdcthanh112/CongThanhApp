package com.congthanh.paymentservice.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long id;

    @NotNull
    private String customer;

    private String note;

    private BigDecimal total;

    private String orderDate;

    private String checkout;

    @NotNull
    private String status;

}
