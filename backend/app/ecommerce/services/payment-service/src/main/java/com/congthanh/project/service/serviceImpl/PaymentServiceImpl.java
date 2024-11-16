package com.congthanh.project.service.serviceImpl;

import com.congthanh.project.constant.enums.PaymentMethod;
import com.congthanh.project.exception.ecommerce.UnsupportedPaymentMethodException;
import com.congthanh.project.model.request.PaymentRequest;
import com.congthanh.project.model.response.PaymentResponse;
import com.congthanh.project.service.PaymentService;
import com.congthanh.project.service.strategy.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final Map<PaymentMethod, PaymentStrategy> paymentStrategies;

    @Autowired
    public PaymentServiceImpl(List<PaymentStrategy> strategyList) {
        this.paymentStrategies = strategyList.stream()
                .collect(Collectors.toMap(
                        PaymentStrategy::paymentMethod,
                        strategy -> strategy
                ));
    }

    @Override
    public PaymentResponse processPayment(PaymentMethod method, PaymentRequest request) {
        PaymentStrategy strategy = paymentStrategies.get(method);
        if (strategy == null) {
            throw new UnsupportedPaymentMethodException("Method" + request.getPaymentMethod() + " is not supported");
        }

        strategy.validatePayment(request);

        try {
            PaymentResponse result = strategy.processPayment(request);
            // Log payment attempt
            return result;
        } catch (Exception e) {
            // Handle exceptions, log errors
            log.error("Catch Lỗi " + request.getPaymentMethod(), request, e);
            throw new RuntimeException("Payment failed", e);
        }
    }

    @Override
    public PaymentResponse executePayment(PaymentMethod method, PaymentRequest request) {
        return null;
    }

//    @KafkaListener(topics = "order-created-topic")
//    private void handleOrderCreated(OrderResponse event) {
//        // Xử lý thanh toán cho đơn hàng
//        Payment payment = null;
//
//        kafkaTemplate.send("payment-completed-topic", payment);
//    }

}

