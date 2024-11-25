package com.congthanh.paymentservice.service.serviceImpl;

import com.congthanh.paymentservice.constant.enums.TransactionStatus;
import com.congthanh.paymentservice.model.entity.PaymentTransaction;
import com.congthanh.paymentservice.exception.ecommerce.NotFoundException;
import com.congthanh.paymentservice.model.request.PaymentRequest;
import com.congthanh.paymentservice.repository.paymentTransaction.PaymentTransactionRepository;
import com.congthanh.paymentservice.service.PaymentTransactionService;
import com.congthanh.paymentservice.utils.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentTransactionServiceImpl implements PaymentTransactionService {

    private final PaymentTransactionRepository paymentTransactionRepository;

    private final SnowflakeIdGenerator snowflakeIdGenerator;

    @Override
    public PaymentTransaction createTransaction(PaymentRequest request) {
        PaymentTransaction transaction = PaymentTransaction.builder()
                .id(snowflakeIdGenerator.nextId())
//                .orderId(request.getOrderId())
                .amount(request.getAmount())
//                .paymentMethod(request.getPaymentMethod())
//                .state(PaymentState.PENDING)
                .createdAt(Instant.now())
                .build();

        return paymentTransactionRepository.save(transaction);
    }

    @Override
    public void updateTransactionStatus(Long transactionId, TransactionStatus newStatus, String providerTransactionId) {
        PaymentTransaction transaction = paymentTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction not found: " +transactionId));

        transaction.setStatus(newStatus);
        transaction.setProviderTransactionId(providerTransactionId);
        transaction.setUpdatedAt(Instant.now());

        paymentTransactionRepository.save(transaction);
    }

    @Override
    public void processRefund(Long transactionId, BigDecimal refundAmount) {
        PaymentTransaction originalTransaction = paymentTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction not found"+transactionId));

        validateRefundEligibility(originalTransaction, refundAmount);

        PaymentTransaction refundTransaction = PaymentTransaction.builder()
                .id(snowflakeIdGenerator.nextId())
//                .originalTransactionId(transactionId)
                .amount(refundAmount.negate())
                .status(TransactionStatus.REFUNDED)
                .createdAt(Instant.now())
                .build();

        paymentTransactionRepository.save(refundTransaction);
        originalTransaction.setStatus(TransactionStatus.REFUNDED);
        paymentTransactionRepository.save(originalTransaction);
    }

    @Override
    public TransactionStatus getTransactionStatus(Long transactionId) {
        return null;
    }

    private void validateRefundEligibility(
            PaymentTransaction transaction,
            BigDecimal refundAmount
    ) {
        if (transaction.getStatus() != TransactionStatus.SUCCESS) {
            throw new RuntimeException("RefundNotAllowedException: Transaction not eligible for refund");
        }
        if (refundAmount.compareTo(transaction.getAmount()) > 0) {
            throw new RuntimeException("InvalidRefundAmountException: Refund amount exceeds original transaction");
        }
    }

}
