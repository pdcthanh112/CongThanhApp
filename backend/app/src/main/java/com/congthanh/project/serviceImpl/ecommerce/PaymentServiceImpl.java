package com.congthanh.project.serviceImpl.ecommerce;

import com.congthanh.project.dto.ecommerce.PaymentDTO;
import com.congthanh.project.entity.ecommerce.Payment;
import com.congthanh.project.enums.ecommerce.PaymentStatus;
import com.congthanh.project.repository.ecommerce.payment.PaymentRepository;
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
