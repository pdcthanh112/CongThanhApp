package com.congthanh.project.service.strategy;

import com.congthanh.project.constant.enums.PaymentMethod;
import com.congthanh.project.constant.enums.PaymentStatus;
import com.congthanh.project.constant.enums.TransactionStatus;
import com.congthanh.project.constant.enums.TransactionType;
import com.congthanh.project.entity.Payment;
import com.congthanh.project.entity.PaymentTransaction;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.model.request.PaymentRequest;
import com.congthanh.project.model.request.RefundRequest;
import com.congthanh.project.model.response.PaymentResponse;
import com.congthanh.project.model.response.RefundResponse;
import com.congthanh.project.repository.PaymentRepository;
import com.congthanh.project.service.PaymentValidator;
import com.congthanh.project.utils.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

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
    public PaymentResponse initializePayment(PaymentRequest request) {
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
    public PaymentResponse processPayment(PaymentRequest request) {
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
