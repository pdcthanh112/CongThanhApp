package com.congthanh.project.service.strategy;

import com.congthanh.project.model.request.PaymentRequest;
import com.congthanh.project.model.request.RefundRequest;
import com.congthanh.project.model.response.PaymentResponse;
import com.congthanh.project.model.response.RefundResponse;

import java.util.Map;

public interface PaymentStrategy {

    PaymentResponse initializePayment(PaymentRequest request);

    PaymentResponse processPayment(PaymentRequest request);

    PaymentResponse refundPayment(String paymentId, RefundRequest request);

    PaymentResponse validatePayment(String paymentId);

    RefundResponse processRefund(RefundRequest request);

}
