package com.congthanh.project.service.strategy;

import com.congthanh.project.constant.enums.PaymentMethod;
import com.congthanh.project.exception.ecommerce.PaymentRetryExhaustedException;
import com.congthanh.project.model.request.PaymentRequest;
import com.congthanh.project.model.request.RefundRequest;
import com.congthanh.project.model.response.PaymentResponse;
import com.congthanh.project.model.response.RefundResponse;
import com.congthanh.project.service.*;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditCardPaymentStrategy implements PaymentStrategy {

//    private final CreditCardGateway gateway;

//    private final StripeService stripeService;

    private final FraudDetectionService fraudDetectionService;

    private final PaymentRetryService retryService;

    private final SecurePaymentEncryptor encryptor;

    private final PaymentValidator paymentValidator;

    @Override
    public PaymentMethod paymentMethod() {
        return PaymentMethod.CREDIT_CARD;
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
        FraudCheckResult fraudCheck = fraudDetectionService.assessFraudRisk(request);
        if (fraudCheck.isSuspicious()) {
            return createFraudBlockedResponse();
        }
        String encryptedCardToken = encryptor.encryptSensitiveData(request.getAdditionalInfo().get("card_token"));

        try {
            // Thực thi với retry mechanism
            PaymentIntent paymentIntent = retryService.executeWithRetry(() ->
                    stripeService.createPaymentIntent(
                            PaymentIntentRequest.builder()
                                    .amount(request.getAmount())
                                    .currency(request.getCurrency())
                                    .paymentMethod(encryptedCardToken)
                                    .build()
                    )
            );

            return createSuccessResponse(paymentIntent);
        } catch (PaymentRetryExhaustedException e) {
            return createFailureResponse(e);
        }
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
