package com.congthanh.paymentservice.model.entity;

import com.congthanh.paymentservice.constant.enums.TransactionStatus;
import com.congthanh.paymentservice.constant.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payment_transaction")
public class PaymentTransaction {

    @Id
    private Long id;

    @ManyToOne
    private Payment payment;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private BigDecimal amount;

    private String currency;

    private String providerTransactionId;

    @CreatedDate
    private Instant createdAt;

    private Instant updatedAt;

}
