package com.congthanh.project.entity;

import com.congthanh.project.constant.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
  private List<OrderDetail> orderDetail;

  @CreatedDate
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @Column(name = "created_at")
  private Instant createdAt;

  @LastModifiedDate
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @Column(name = "updated_at")
  private Instant updatedAt;

}
