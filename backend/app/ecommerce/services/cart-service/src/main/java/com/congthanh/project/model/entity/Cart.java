package com.congthanh.project.model.entity;

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
@Table(name = "cart")
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String name;

  private String customer;

  @Column(name = "created_at")
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  private String status;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CartItem> cartItems;

  @Column(name = "is_default")
  @JsonProperty("isDefault")
  private boolean isDefault;

//  @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//  @JsonBackReference
//  @JsonIgnore
//  @ToString.Exclude
//  @EqualsAndHashCode.Exclude
//  private Long checkout;

  @PrePersist
  public void prePersist() {
    this.createdAt = Instant.now();
  }


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
