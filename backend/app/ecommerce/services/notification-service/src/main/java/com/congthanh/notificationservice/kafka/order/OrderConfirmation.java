package com.congthanh.notificationservice.kafka.order;

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
public class OrderConfirmation {

    private String orderReference;

    private BigDecimal totalAmount;

    private PaymentMethod paymentMethod;

    private Customer customer;

    private List<Product> products;

}
