package com.congthanh.project.service.processor;

import com.congthanh.project.model.request.PaymentRequest;
import com.congthanh.project.model.request.RefundRequest;
import com.congthanh.project.model.response.PaymentResponse;
import com.congthanh.project.model.response.RefundResponse;

import java.util.Map;

public interface PaymentProcessor {

    PaymentResponse initializePayment(PaymentRequest request);
    PaymentResponse processPayment(String paymentId, Map<String, String> params);
    PaymentResponse validatePayment(String paymentId);
    RefundResponse processRefund(RefundRequest request);

}
