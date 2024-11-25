package com.congthanh.customerservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCheckoutRequest {

    private String customer;

    private String cartId;

    private BigDecimal total;

    private String voucher;

    private String address;

    private String phone;

    private String payment;

}
