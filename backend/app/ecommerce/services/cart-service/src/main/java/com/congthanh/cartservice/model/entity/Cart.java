package com.congthanh.cartservice.model.entity;

import com.congthanh.cartservice.constant.enums.CartStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "carts")
public class Cart {

  @Id
  private Long id;

  private String name;

  @Column(name = "customer_id", nullable = false, updatable = false)
  private String customerId;

  @Column(name = "created_at", updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  @Enumerated(EnumType.STRING)
  private CartStatus status;

  @OneToMany(mappedBy = "cartId", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CartItem> cartItems;

  @Column(name = "is_default")
  @JsonProperty("isDefault")
  private boolean isDefault;

  @Version
  private int version;


//  @Transient
//  public BigDecimal getTotalOrderPrice() {
//    BigDecimal sum = BigDecimal.ZERO;
//    if (this.cartItems == null || this.cartItems.isEmpty()) {
//      return BigDecimal.ZERO;
//    }
//    Set<CartItem> orderProducts = this.cartItems;
//    for (CartItem item : orderProducts) {
//      BigDecimal quantityDecimal = BigDecimal.valueOf(item.getQuantity());
//      BigDecimal itemTotal = quantityDecimal.multiply(item.getProduct().getPrice());
//      sum = sum.add(itemTotal);
//    }
//    return sum;
//  }
//
//  public int getCountItem() {
//    if (this.cartItems == null || this.cartItems.isEmpty()) {
//      return 0;
//    }
//    return this.cartItems.size();
//  }

}
