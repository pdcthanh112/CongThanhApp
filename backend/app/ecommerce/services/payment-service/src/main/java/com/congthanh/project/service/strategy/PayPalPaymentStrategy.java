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
            return PaymentResponse.builder()
                    .orderId(result.getId())
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
    public void validatePayment(PaymentRequest request) {
        paymentValidator.validate(request);
    }

    @Override
    public RefundResponse processRefund(RefundRequest request) {
        return null;
    }

    //trên giao diện bấm thanh toán thì gọi hàm này để hoàn tất giao dịch
    //hàm trên chỉ để tạo thanh toán và chuyển đến trang thanh toán
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecution);
    }

}
