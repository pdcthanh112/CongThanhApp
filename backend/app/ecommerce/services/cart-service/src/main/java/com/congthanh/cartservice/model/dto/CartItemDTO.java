package com.congthanh.cartservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private Long id;

  private Long cart;

  private String product;

  private int quantity;

  private Instant createdAt;
}
