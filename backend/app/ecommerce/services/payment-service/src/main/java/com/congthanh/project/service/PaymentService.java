package com.congthanh.project.service;

import com.congthanh.project.constant.enums.PaymentMethod;
import com.congthanh.project.model.request.PaymentRequest;
import com.congthanh.project.model.response.PaymentResponse;

public interface PaymentService {

    PaymentResponse processPayment(PaymentMethod method, PaymentRequest request);

    PaymentResponse executePayment(PaymentMethod method, PaymentRequest request);
}
