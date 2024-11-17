package com.congthanh.project.service.strategy;

import com.congthanh.project.constant.enums.PaymentMethod;
import com.congthanh.project.model.request.PaymentRequest;
import com.congthanh.project.model.request.RefundRequest;
import com.congthanh.project.model.response.PaymentResponse;
import com.congthanh.project.model.response.RefundResponse;
import com.congthanh.project.service.PaymentValidator;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PayPalPaymentStrategy implements PaymentStrategy {

    private final APIContext apiContext;

    private final PaymentValidator paymentValidator;

    @Override
    public PaymentMethod paymentMethod() {
        return PaymentMethod.PAYPAL;
    }

    @Override
    public PaymentResponse initializePayment(PaymentRequest request) {
        return null;
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        Amount amount = new Amount();
        amount.setCurrency(request.getCurrency());
        amount.setTotal(String.format("%.2f", request.getAmount()));

        Transaction transaction = new Transaction();
        transaction.setDescription(request.getDescription());
        transaction.setAmount(amount);

        Payer payer = new Payer();
        payer.setPaymentMethod(request.getPaymentMethod().toString().toLowerCase());

        Payment payment = new Payment();
        payment.setIntent(request.getAdditionalInfo().get("intent"));
        payment.setPayer(payer);
        payment.setTransactions(Arrays.asList(transaction));

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(request.getAdditionalInfo().get("cancelUrl"));
        redirectUrls.setReturnUrl(request.getAdditionalInfo().get("returnUrl"));
        payment.setRedirectUrls(redirectUrls);

        try {
            Payment result = payment.create(apiContext);
            System.out.println("RESULTTTTTTTTTTTTTTTTTT"+result);
            return PaymentResponse.builder()
                    .paymentId(result.getId())
                    .paymentMethod(PaymentMethod.PAYPAL)
                    .additionalInfo(result.getLinks().stream()
                            .filter(link -> link.getRel().equals("approval_url"))
                            .findFirst()
                            .map(link -> {
                                Map<String, String> map = new HashMap<>();
                                map.put("approvalUrl", link.getHref());
                                return map;
                            })
                            .orElseGet(HashMap::new))
                    .build();

//            Map<String, String> additionalInfo = new HashMap<>();
//            for (Links links : result.getLinks()) {
//                if (links.getRel().equals("approval_url")) {
//                    additionalInfo.put("approvalUrl", links.getHref());
//                }
//            }
//            response.setAdditionalInfo(additionalInfo);

//            return response;
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public PaymentResponse executePayment(PaymentRequest request) {
        System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
        String paymentId = request.getAdditionalInfo().get("paymentId");
        String payerId = request.getAdditionalInfo().get("payerId");
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        try {
            Payment result = payment.execute(apiContext, paymentExecution);
            System.out.println("SÊRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR"+result);
            return null;
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }
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
