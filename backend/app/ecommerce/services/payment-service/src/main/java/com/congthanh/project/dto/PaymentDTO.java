package com.congthanh.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {

    private Long id;

    private BigDecimal amount;

    private String paymentMethod;

    private Long createdDate;

    private Long paymentDate;

    private String status;

}
