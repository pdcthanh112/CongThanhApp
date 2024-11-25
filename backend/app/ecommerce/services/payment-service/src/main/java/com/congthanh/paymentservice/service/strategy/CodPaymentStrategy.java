package com.congthanh.paymentservice.service.strategy;

import com.congthanh.paymentservice.constant.enums.PaymentMethod;
import com.congthanh.paymentservice.constant.enums.PaymentStatus;
import com.congthanh.paymentservice.constant.enums.TransactionStatus;
import com.congthanh.paymentservice.constant.enums.TransactionType;
import com.congthanh.paymentservice.model.entity.Payment;
import com.congthanh.paymentservice.model.entity.PaymentTransaction;
import com.congthanh.paymentservice.exception.ecommerce.NotFoundException;
import com.congthanh.paymentservice.model.request.PaymentRequest;
import com.congthanh.paymentservice.model.request.RefundRequest;
import com.congthanh.paymentservice.model.response.PaymentResponse;
import com.congthanh.paymentservice.model.response.RefundResponse;
import com.congthanh.paymentservice.repository.payment.PaymentRepository;
import com.congthanh.paymentservice.service.PaymentValidator;
import com.congthanh.paymentservice.utils.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CodPaymentStrategy implements PaymentStrategy {

    private final PaymentRepository paymentRepository;

    private final SnowflakeIdGenerator snowflakeIdGenerator;

    private final PaymentValidator paymentValidator;

    @Override
    public PaymentMethod paymentMethod() {
        return PaymentMethod.CASH_ON_DELIVERY;
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        Payment payment = Payment.builder()
                .id(snowflakeIdGenerator.nextId())
                .paymentMethod(PaymentMethod.CASH_ON_DELIVERY)
                .status(PaymentStatus.PENDING)
                .amount(request.getAmount())
                .currency(request.getCurrency())
//                .order(request.getOrder())
                .build();

        var result = paymentRepository.save(payment);

        // Update order status

        return PaymentResponse.builder()
                .paymentId(result.getId().toString())
                .status(PaymentStatus.PENDING)
                .build();
    }

    @Override
    public PaymentResponse executePayment(PaymentRequest request) {
        Payment payment = paymentRepository.findById(request.getOrderId())
                .orElseThrow(() -> new NotFoundException("Payment not found" + request.getPaymentMethod()));

        // COD payment is marked as CONFIRMED when order is confirmed
        payment.setStatus(PaymentStatus.PROCESSING);
        payment = paymentRepository.save(payment);

        // Create transaction record
        PaymentTransaction transaction = PaymentTransaction.builder()
                .payment(payment)
                .type(TransactionType.AUTHORIZE)
                .status(TransactionStatus.SUCCESS)
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .build();

        payment.getTransactions().add(transaction);

        return PaymentResponse.builder()
                .paymentId(payment.getId().toString())
                .status(payment.getStatus())
                .build();
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
