package com.congthanh.paymentservice.service;

import com.congthanh.paymentservice.constant.enums.PaymentMethod;
import com.congthanh.paymentservice.model.request.PaymentRequest;
import com.congthanh.paymentservice.model.response.PaymentResponse;

public interface PaymentService {

    PaymentResponse processPayment(PaymentMethod method, PaymentRequest request);

    PaymentResponse executePayment(PaymentMethod method, PaymentRequest request);
}
