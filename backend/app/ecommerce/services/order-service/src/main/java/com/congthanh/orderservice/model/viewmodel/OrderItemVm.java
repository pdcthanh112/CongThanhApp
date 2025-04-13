package com.congthanh.orderservice.model.viewmodel;

import java.math.BigDecimal;

public record OrderItemVm(
        Long id,
        String product,
        int quantity,
        BigDecimal orderPrice
) {
}
