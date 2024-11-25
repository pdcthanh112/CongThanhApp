package com.congthanh.paymentservice.service.strategy;

import com.congthanh.paymentservice.constant.enums.PaymentMethod;
import com.congthanh.paymentservice.model.request.PaymentRequest;
import com.congthanh.paymentservice.model.request.RefundRequest;
import com.congthanh.paymentservice.model.response.PaymentResponse;
import com.congthanh.paymentservice.model.response.RefundResponse;

public interface PaymentStrategy {

    PaymentMethod paymentMethod();

    PaymentResponse processPayment(PaymentRequest request);

    PaymentResponse executePayment(PaymentRequest request);

    PaymentResponse refundPayment(String paymentId, RefundRequest request);

    void validatePayment(PaymentRequest request);

    RefundResponse processRefund(RefundRequest request);

}
