package com.congthanh.paymentservice.service.strategy;

import com.congthanh.paymentservice.constant.enums.PaymentMethod;
import com.congthanh.paymentservice.model.request.PaymentRequest;
import com.congthanh.paymentservice.model.request.RefundRequest;
import com.congthanh.paymentservice.model.response.PaymentResponse;
import com.congthanh.paymentservice.model.response.RefundResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BitcoinPaymentStrategy implements PaymentStrategy {

    @Override
    public PaymentMethod paymentMethod() {
        return null;
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        return null;
    }

    @Override
    public PaymentResponse executePayment(PaymentRequest request) {
        return null;
    }

    @Override
    public PaymentResponse refundPayment(String paymentId, RefundRequest request) {
        return null;
    }

    @Override
    public void validatePayment(PaymentRequest request) {

    }

    @Override
    public RefundResponse processRefund(RefundRequest request) {
        return null;
    }
}
