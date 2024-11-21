package com.congthanh.project.service.strategy;

import com.congthanh.project.constant.enums.PaymentMethod;
import com.congthanh.project.model.request.PaymentRequest;
import com.congthanh.project.model.request.RefundRequest;
import com.congthanh.project.model.response.PaymentResponse;
import com.congthanh.project.model.response.RefundResponse;

import java.util.Map;

public interface PaymentStrategy {

    PaymentMethod paymentMethod();

    PaymentResponse processPayment(PaymentRequest request);

    PaymentResponse executePayment(PaymentRequest request);

    PaymentResponse refundPayment(String paymentId, RefundRequest request);

    void validatePayment(PaymentRequest request);

    RefundResponse processRefund(RefundRequest request);

}
