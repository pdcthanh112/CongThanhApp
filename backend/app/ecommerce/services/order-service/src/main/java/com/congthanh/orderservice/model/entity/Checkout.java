package com.congthanh.orderservice.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "checkout")
public class Checkout {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String customer;

  @Column(precision = 38, scale = 2)
  private BigDecimal total;

  private String address;

  private String phone;

//  @OneToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "payment", referencedColumnName = "id", nullable = false)
//  @JsonManagedReference
  private Long payment;

//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "voucher")
  private String voucher;

  @Column(name = "checkout_date")
  private Instant checkoutDate;

//  @OneToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "cart", referencedColumnName = "id", nullable = false)
//  @JsonManagedReference
  private String cart;

  @OneToOne(mappedBy = "checkout", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonBackReference
  @JsonIgnore
  private Order order;

}
