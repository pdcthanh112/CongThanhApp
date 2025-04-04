package com.congthanh.orderservice.model.entity;

import com.congthanh.orderservice.constant.enums.OrderStatus;
import com.congthanh.orderservice.constant.enums.PaymentStatus;
import com.congthanh.orderservice.constant.enums.ShippingStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Table(name = "`orders`")
public class Order extends AbstractAuditEntity{

  @Id
  private Long id;

  @Column(name = "customer_id", nullable = false)
  private String customer;

  @Column(name = "shipping_address_id", nullable = false)
  private Long shippingAddress;

  @Column(name = "billing_address_id", nullable = false)
  private Long billingAddress;

  @Column(columnDefinition = "text")
  private String note;

  @Column(name = "total_amount", precision = 38, scale = 2)
  private BigDecimal totalAmount;

  @Column(name = "order_date")
  private Instant orderDate;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @Column(name = "payment_status")
  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;

  @Column(name = "shipping_status")
  @Enumerated(EnumType.STRING)
  private ShippingStatus shippingStatus;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "checkout", nullable = false, referencedColumnName = "id")
  @JsonManagedReference
  private Checkout checkout;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<OrderItem> orderItem = new ArrayList<>();

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<OrderItem> statusTracking = new ArrayList<>();
}
