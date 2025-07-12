package com.congthanh.paymentservice.model.entity;

import com.congthanh.paymentservice.constant.enums.PaymentMethod;
import com.congthanh.paymentservice.constant.enums.PaymentStatus;
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
@Table(name = "payments")
public class Payment {

    @Id
    private Long id;

    @Column(name = "amount", precision = 38, scale = 2)
    private BigDecimal amount;

    private String currency;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "created_at")
    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "payment_at")
    private Instant paymentAt;

    private String transactionId;  // ID từ payment provider

    private String paymentUrl;     // URL redirect để thanh toán (nếu có)

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "order_id")
    private Long orderId;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    private List<PaymentTransaction> transactions;
}
