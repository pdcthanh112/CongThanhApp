package com.congthanh.project.service;

import com.congthanh.project.dto.PaymentDTO;
import com.congthanh.project.entity.ecommerce.Payment;

public interface PaymentService {

    Payment createPayment(PaymentDTO paymentDTO);
}
