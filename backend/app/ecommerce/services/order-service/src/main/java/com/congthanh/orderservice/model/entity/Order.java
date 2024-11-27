package com.congthanh.orderservice.model.entity;

import com.congthanh.orderservice.constant.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Table(name = "`Order`")
public class Order extends AbstractAuditEntity{

  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String customer;

  @Column(columnDefinition = "text")
  private String note;

  @Column(precision = 38, scale = 2)
  private BigDecimal total;

  @Column(name = "order_date")
  private Instant orderDate;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "checkout", nullable = false, referencedColumnName = "id")
  @JsonManagedReference
  private Checkout checkout;

  @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<OrderItem> orderItem;

}
