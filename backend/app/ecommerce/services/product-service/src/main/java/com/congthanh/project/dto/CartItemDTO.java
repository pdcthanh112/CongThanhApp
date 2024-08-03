package com.congthanh.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {

  private String id;

  private ProductDTO product;

  private int quantity;

  private CartDTO cart;

  private long createdDate;

}
