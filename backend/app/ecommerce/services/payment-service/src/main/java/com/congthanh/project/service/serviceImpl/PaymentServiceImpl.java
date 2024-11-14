package com.congthanh.project.service.serviceImpl;

import com.congthanh.project.constant.enums.PaymentMethod;
import com.congthanh.project.exception.ecommerce.UnsupportedPaymentMethodException;
import com.congthanh.project.model.request.PaymentRequest;
import com.congthanh.project.model.response.PaymentResponse;
import com.congthanh.project.service.PaymentService;
import com.congthanh.project.service.PaymentValidator;
import com.congthanh.project.service.strategy.CodPaymentStrategy;
import com.congthanh.project.service.strategy.CreditCardPaymentStrategy;
import com.congthanh.project.service.strategy.PayPalPaymentStrategy;
import com.congthanh.project.service.strategy.PaymentStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
//@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final Map<PaymentMethod, PaymentStrategy> paymentStrategies;

    private final PaymentValidator paymentValidator;

    public PaymentServiceImpl(
            CodPaymentStrategy codStrategy,
            CreditCardPaymentStrategy cardStrategy,
            PayPalPaymentStrategy paypalStrategy,
            PaymentValidator paymentValidator
    ) {
        paymentStrategies = new HashMap<>();
        paymentStrategies.put(PaymentMethod.CASH_ON_DELIVERY, codStrategy);
        paymentStrategies.put(PaymentMethod.CREDIT_DEBIT_CARD, cardStrategy);
        paymentStrategies.put(PaymentMethod.PAYPAL, paypalStrategy);
        this.paymentValidator = paymentValidator;
    }

    @Override
    public PaymentResponse processPayment(PaymentMethod method, PaymentRequest request) {
        PaymentStrategy strategy = paymentStrategies.get(method);
        if (strategy == null) {
            throw new UnsupportedPaymentMethodException("Method" + request.getPaymentMethod() + " is not supported");
        }

        // Validate request
        paymentValidator.validate(request);

        // Process payment using selected strategy
        try {
            PaymentResponse result = strategy.processPayment(request);
            // Log payment attempt
            return result;
        } catch (Exception e) {
            // Handle exceptions, log errors
            log.error("Catch Lỗi "+request.getPaymentMethod(), request, e);
            throw new RuntimeException("Payment failed", e);
        }
    }

//    @KafkaListener(topics = "order-created-topic")
//    private void handleOrderCreated(OrderResponse event) {
//        // Xử lý thanh toán cho đơn hàng
//        Payment payment = null;
//
//        kafkaTemplate.send("payment-completed-topic", payment);
//    }

}

