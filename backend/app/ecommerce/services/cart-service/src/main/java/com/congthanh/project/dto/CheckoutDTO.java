package com.congthanh.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutDTO {

  private Long id;

  private String customer;

  private BigDecimal total;

  private String address;

  private String phone;

  private String paymentMethod;

  private long checkoutDate;

  private CartDTO cart;

  private String voucher;

}
