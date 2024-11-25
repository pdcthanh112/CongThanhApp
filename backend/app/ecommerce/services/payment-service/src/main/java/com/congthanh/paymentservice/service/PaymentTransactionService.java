package com.congthanh.paymentservice.service;

import com.congthanh.paymentservice.constant.enums.TransactionStatus;
import com.congthanh.paymentservice.model.entity.PaymentTransaction;
import com.congthanh.paymentservice.model.request.PaymentRequest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface PaymentTransactionService {

    PaymentTransaction createTransaction(PaymentRequest request);

    void updateTransactionStatus(Long transactionId, TransactionStatus newStatus, String providerTransactionId);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    void processRefund(Long transactionId, BigDecimal refundAmount);

    TransactionStatus getTransactionStatus(Long transactionId);

}
