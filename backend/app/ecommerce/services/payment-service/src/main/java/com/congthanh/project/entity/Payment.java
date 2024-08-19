package com.congthanh.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", precision = 38, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "created_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant createdDate;

    @Column(name = "payment_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant paymentDate;

    private String status;

//    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonBackReference
//    @JsonIgnore
    private Long checkout;
}
