package com.congthanh.project.dto;

import com.congthanh.project.model.response.CartResponse;
import com.congthanh.project.model.response.VoucherResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

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

  private long checkoutDate;

  private CartResponse cart;

  private VoucherResponse voucher;

}
