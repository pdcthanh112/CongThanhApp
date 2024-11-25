package com.congthanh.orderservice.model.dto;

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
public class CheckoutDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private Long id;

  private String customer;

  private BigDecimal total;

  private String address;

  private String phone;

  private String paymentMethod;

  private Instant checkoutDate;

  private CartResponse cart;

  private PromotionDTO voucher;

}
