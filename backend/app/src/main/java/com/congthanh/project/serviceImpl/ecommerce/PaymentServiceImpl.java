package com.congthanh.project.serviceImpl.ecommerce;

import com.congthanh.project.dto.ecommerce.PaymentDTO;
import com.congthanh.project.entity.ecommerce.Payment;
import com.congthanh.project.enums.ecommerce.PaymentStatus;
import com.congthanh.project.model.ecommerce.mapper.PaymentMapper;
import com.congthanh.project.repository.ecommerce.payment.PaymentRepository;
import com.congthanh.project.service.ecommerce.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    public Payment createPayment(PaymentDTO paymentDTO) {
        Payment payment = Payment.builder()
                .amount(paymentDTO.getAmount())
                .paymentMethod(paymentDTO.getPaymentMethod())
                .createdDate(Instant.now().toEpochMilli())
                .status(PaymentStatus.NEW.name())
                .build();
        Payment result = paymentRepository.createPayment(payment);
        return result;
    }
}
