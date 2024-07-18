package com.congthanh.project.service.ecommerce;

import com.congthanh.project.dto.ecommerce.PaymentDTO;
import com.congthanh.project.entity.ecommerce.Payment;

public interface PaymentService {

    Payment createPayment(PaymentDTO paymentDTO);
}
