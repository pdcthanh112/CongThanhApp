package com.congthanh.notificationservice.kafka.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentConfirmation {

    String orderReference;

    BigDecimal amount;

    PaymentMethod paymentMethod;

    String customerFirstname;

    String customerLastname;

    String customerEmail;

}
