package com.congthanh.project.service.strategy;

import com.congthanh.project.constant.enums.PaymentMethod;
import com.congthanh.project.model.request.PaymentRequest;
import com.congthanh.project.model.request.RefundRequest;
import com.congthanh.project.model.response.PaymentResponse;
import com.congthanh.project.model.response.RefundResponse;
import com.congthanh.project.service.PaymentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CreditCardPaymentStrategy implements PaymentStrategy {

//    private final CreditCardGateway gateway;

    private final PaymentValidator paymentValidator;

    @Override
    public PaymentMethod paymentMethod() {
        return PaymentMethod.CREDIT_DEBIT_CARD;
    }

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
    public PaymentResponse executePayment(PaymentRequest request) {
        return null;
    }

    @Override
    public PaymentResponse refundPayment(String paymentId, RefundRequest request) {
        return null;
    }

    @Override
    public void validatePayment(PaymentRequest request) {
        paymentValidator.validate(request);
    }

    @Override
    public RefundResponse processRefund(RefundRequest request) {
        return null;
    }

}
