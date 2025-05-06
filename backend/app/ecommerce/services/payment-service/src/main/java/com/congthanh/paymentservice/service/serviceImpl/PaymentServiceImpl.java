package com.congthanh.paymentservice.service.serviceImpl;

import com.congthanh.paymentservice.constant.enums.PaymentMethod;
import com.congthanh.paymentservice.model.entity.Payment;
import com.congthanh.paymentservice.model.request.PaymentRequest;
import com.congthanh.paymentservice.model.response.PaymentResponse;
import com.congthanh.paymentservice.repository.payment.PaymentRepository;
import com.congthanh.paymentservice.service.PaymentService;
import com.congthanh.paymentservice.service.factory.PaymentStrategyFactory;
import com.congthanh.paymentservice.service.strategy.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentStrategyFactory strategyFactory;

    private KafkaTemplate<String, Object> kafkaTemplate;

    private final PaymentRepository paymentRepository;

    private static final String PAYMENT_PROCESSED_TOPIC = "payment-processed";
    private static final String PAYMENT_FAILED_TOPIC = "payment-failed";

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

    @KafkaListener(topics = "order-created")
    public void processPayment(OrderCreatedEvent event) {
        try {
            // Ghi log
            log.info("Processing payment for order: {}", event.getOrderDTO().getOrderId());

            // Thực hiện thanh toán với payment gateway
            PaymentGatewayResponse gatewayResponse = processPaymentWithGateway(event.getOrderDTO());

            // Lưu thông tin thanh toán
            Payment payment = new Payment();
            payment.setOrderId(event.getOrderDTO().getOrderId());
            payment.setAmount(event.getOrderDTO().getTotalAmount());
            payment.setTransactionId(gatewayResponse.getTransactionId());

            if (gatewayResponse.isSuccess()) {
                payment.setStatus(PaymentStatus.COMPLETED);
                paymentRepository.save(payment);

                // Gửi sự kiện thanh toán thành công
                PaymentProcessedEvent processedEvent = new PaymentProcessedEvent();
                processedEvent.setSagaId(event.getSagaId());
                processedEvent.setOrderDTO(event.getOrderDTO());
                processedEvent.setTransactionId(gatewayResponse.getTransactionId());

                kafkaTemplate.send(PAYMENT_PROCESSED_TOPIC, processedEvent);

                log.info("Payment successful for order: {}", event.getOrderDTO().getOrderId());
            } else {
                payment.setStatus(PaymentStatus.FAILED);
                payment.setFailureReason(gatewayResponse.getErrorMessage());
                paymentRepository.save(payment);

                // Gửi sự kiện thanh toán thất bại
                PaymentFailedEvent failedEvent = new PaymentFailedEvent();
                failedEvent.setSagaId(event.getSagaId());
                failedEvent.setOrderDTO(event.getOrderDTO());
                failedEvent.setReason(gatewayResponse.getErrorMessage());

                kafkaTemplate.send(PAYMENT_FAILED_TOPIC, failedEvent);

                log.error("Payment failed for order: {}", event.getOrderDTO().getOrderId());
            }
        } catch (Exception e) {
            log.error("Error processing payment for order {}: {}", event.getOrderDTO().getOrderId(), e.getMessage());

            // Gửi sự kiện thanh toán thất bại
            PaymentFailedEvent failedEvent = new PaymentFailedEvent();
            failedEvent.setSagaId(event.getSagaId());
            failedEvent.setOrderDTO(event.getOrderDTO());
            failedEvent.setReason("System error: " + e.getMessage());

            kafkaTemplate.send(PAYMENT_FAILED_TOPIC, failedEvent);
        }
    }

    // Xử lý yêu cầu hoàn tiền
    @KafkaListener(topics = {"inventory-failed", "delivery-failed"})
    public void processRefund(PaymentRefundEvent event) {
        try {
            // Tìm giao dịch thanh toán
            Payment payment = paymentRepository.findByOrderId(event.getOrderId())
                    .orElseThrow(() -> new PaymentNotFoundException("Payment not found for order: " + event.getOrderId()));

            // Thực hiện hoàn tiền với payment gateway
            RefundGatewayResponse gatewayResponse = processRefundWithGateway(payment.getTransactionId(), event.getAmount());

            if (gatewayResponse.isSuccess()) {
                // Cập nhật trạng thái thanh toán
                payment.setStatus(PaymentStatus.REFUNDED);
                payment.setRefundTransactionId(gatewayResponse.getRefundTransactionId());
                paymentRepository.save(payment);

                log.info("Payment refunded for order: {}", event.getOrderId());
            } else {
                log.error("Refund failed for order {}: {}", event.getOrderId(), gatewayResponse.getErrorMessage());
            }
        } catch (Exception e) {
            log.error("Error processing refund for order {}: {}", event.getOrderId(), e.getMessage());
        }
    }


}

