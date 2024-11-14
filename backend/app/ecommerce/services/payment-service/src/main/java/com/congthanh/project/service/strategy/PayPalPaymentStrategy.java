package com.congthanh.project.service.strategy;

import com.congthanh.project.model.request.PaymentRequest;
import com.congthanh.project.model.request.RefundRequest;
import com.congthanh.project.model.response.PaymentResponse;
import com.congthanh.project.model.response.RefundResponse;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PayPalPaymentStrategy implements PaymentStrategy {

    private final APIContext apiContext;

//    private final PayPalClient paypalClient;

    @Autowired
    public PayPalPaymentStrategy(@Value("${paypal.client.id}") String clientId,
                                 @Value("${paypal.client.secret}") String clientSecret,
                                 @Value("${paypal.mode}") String mode) {
        this.apiContext = new APIContext(clientId, clientSecret, mode);
    }

    @Override
    public PaymentResponse initializePayment(PaymentRequest request) {
        return null;
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        System.out.println("paypal strategy");
//        PayPalPayment payment = paypalClient.createPayment(
//                request.getAmount(),
//                request.getCurrency(),
//                "http://your-domain/payment/paypal/callback"
//        );
//
//        return new PaymentResult(
//                payment.getId(),
//                PaymentStatus.PENDING,
//                payment.getApprovalUrl()
//        );
        Amount amount = new Amount();
        amount.setCurrency(request.getCurrency());
        amount.setTotal(String.format("%.2f", request.getAmount()));

        Transaction transaction = new Transaction();
        transaction.setDescription(request.getDescription());
        transaction.setAmount(amount);

        Payer payer = new Payer();
        payer.setPaymentMethod(request.getPaymentMethod().toString().toLowerCase());

        Payment payment = new Payment();
        payment.setIntent("order");
        payment.setPayer(payer);
        payment.setTransactions(Arrays.asList(transaction));
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("canceUrl");
        redirectUrls.setReturnUrl("successUrl");
        payment.setRedirectUrls(redirectUrls);

//        return payment.create(apiContext);
        try {
            Payment payment1 = payment.create(apiContext);
            System.out.println("RRRRRRRRRRRRRRR" + payment1);
            return PaymentResponse.builder()
                    .orderId(payment1.getId())
                    .build();
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }


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

    public Payment createPayment(Double total, String currency, String method,
                                 String intent, String description, String cancelUrl, String successUrl) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toLowerCase());

        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(Arrays.asList(transaction));
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecution);
    }

}
