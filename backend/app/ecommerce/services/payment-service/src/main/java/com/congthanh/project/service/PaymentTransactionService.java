package com.congthanh.project.service;

import com.congthanh.project.constant.enums.PaymentStatus;
import com.congthanh.project.constant.enums.TransactionStatus;
import com.congthanh.project.entity.PaymentTransaction;
import com.congthanh.project.model.request.PaymentRequest;
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
