package com.congthanh.project.entity.ecommerce;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cart_item")
public class CartItem {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product", nullable = false)
  private Product product;

  private int quantity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cart", nullable = false)
  private Cart cart;

  @Column(name = "created_at")
  private long createdAt;

  public BigDecimal getTotal() {
    return product.getPrice().multiply(BigDecimal.valueOf(quantity));
  }

}
