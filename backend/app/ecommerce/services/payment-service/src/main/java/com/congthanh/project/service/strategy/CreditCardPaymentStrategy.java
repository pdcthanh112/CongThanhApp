package com.congthanh.project.service.strategy;

import com.congthanh.project.model.request.PaymentRequest;
import com.congthanh.project.model.request.RefundRequest;
import com.congthanh.project.model.response.PaymentResponse;
import com.congthanh.project.model.response.RefundResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CreditCardPaymentStrategy implements PaymentStrategy {

//    private final CreditCardGateway gateway;

    @Override
    public PaymentResponse initializePayment(PaymentRequest request) {
        return null;
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
//        GatewayResponse response = gateway.processPayment(
//                request.getAmount(),
//                request.getAdditionalData().get("cardNumber"),
//                request.getAdditionalData().get("cvv")
//        );
//
//        return new PaymentResult(
//                response.getTransactionId(),
//                mapGatewayStatus(response.getStatus()),
//                response.getMessage()
//        );
        return null;
    }

    @Override
    public PaymentResponse refundPayment(String paymentId, RefundRequest request) {
        return null;
    }

    @Override
    public PaymentResponse validatePayment(String paymentId) {
        return null;
    }

    @Override
    public RefundResponse processRefund(RefundRequest request) {
        return null;
    }

}
