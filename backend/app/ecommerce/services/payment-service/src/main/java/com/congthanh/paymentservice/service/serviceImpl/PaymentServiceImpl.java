package com.congthanh.paymentservice.service.serviceImpl;

import com.congthanh.paymentservice.constant.enums.PaymentMethod;
import com.congthanh.paymentservice.model.request.PaymentRequest;
import com.congthanh.paymentservice.model.response.PaymentResponse;
import com.congthanh.paymentservice.service.PaymentService;
import com.congthanh.paymentservice.service.factory.PaymentStrategyFactory;
import com.congthanh.paymentservice.service.strategy.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentStrategyFactory strategyFactory;

    @Override
    public PaymentResponse processPayment(PaymentMethod method, PaymentRequest request) {
        PaymentStrategy strategy = strategyFactory.createPaymentStrategy(request.getPaymentMethod());

        strategy.validatePayment(request);

        try {
            PaymentResponse result = strategy.processPayment(request);
            log.info("Payment processed successfully");
            return result;
        } catch (Exception e) {
            // Handle exceptions, log errors
            log.error("Catch Lỗi " + request.getPaymentMethod(), request, e);
            throw new RuntimeException("Payment failed", e);
        }
    }

    @Override
    public PaymentResponse executePayment(PaymentMethod method, PaymentRequest request) {
        PaymentStrategy strategy = strategyFactory.createPaymentStrategy(request.getPaymentMethod());

        PaymentResponse result = strategy.executePayment(request);
        return result;
    }

//    @KafkaListener(topics = "order-created-topic")
//    private void handleOrderCreated(OrderResponse event) {
//        // Xử lý thanh toán cho đơn hàng
//        Payment payment = null;
//
//        kafkaTemplate.send("payment-completed-topic", payment);
//    }

}

