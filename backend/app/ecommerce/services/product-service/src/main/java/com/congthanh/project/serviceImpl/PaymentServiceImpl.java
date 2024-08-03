package com.congthanh.project.serviceImpl.ecommerce;

import com.congthanh.project.dto.PaymentDTO;
import com.congthanh.project.entity.Payment;
import com.congthanh.project.enums.PaymentStatus;
import com.congthanh.project.repository.payment.PaymentRepository;
import com.congthanh.project.service.ecommerce.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

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
