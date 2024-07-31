package com.congthanh.project.service;

import com.congthanh.project.dto.ecommerce.PaymentDTO;
import com.congthanh.project.entity.ecommerce.Payment;

public interface PaymentService {

    Payment createPayment(PaymentDTO paymentDTO);
}
