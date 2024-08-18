package com.congthanh.project.serviceImpl;

import com.congthanh.project.dto.PaymentDTO;
import com.congthanh.project.entity.Payment;
import com.congthanh.project.enums.PaymentStatus;
import com.congthanh.project.repository.PaymentRepository;
import com.congthanh.project.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final KafkaTemplate<String, Payment> kafkaTemplate;

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

    @KafkaListener(topics = "order-created-topic")
    private void handleOrderCreated(Order event) {
        // Xử lý thanh toán cho đơn hàng
        Payment payment = paymentProcessor.processPayment(event.getOrderId());

        kafkaTemplate.send("payment-completed-topic", payment);
    }
}
