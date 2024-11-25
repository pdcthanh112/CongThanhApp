package com.congthanh.cartservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private String id;

  private ProductDTO product;

  private int quantity;

  private CartDTO cart;

  private long createdDate;

}
