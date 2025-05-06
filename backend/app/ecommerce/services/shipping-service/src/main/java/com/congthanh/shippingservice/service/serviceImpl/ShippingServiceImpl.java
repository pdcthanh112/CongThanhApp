package com.congthanh.shippingservice.service.serviceImpl;

import com.congthanh.shippingservice.constant.enums.ShippingStatus;
import com.congthanh.shippingservice.exception.ecommerce.NotFoundException;
import com.congthanh.shippingservice.model.dto.ShippingDTO;
import com.congthanh.shippingservice.model.entity.Shipping;
import com.congthanh.shippingservice.repository.ShippingRepository;
import com.congthanh.shippingservice.service.ShippingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingServiceImpl implements ShippingService {

    private final ShippingRepository shippingRepository;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String DELIVERY_CREATED_TOPIC = "delivery-created";
    private static final String DELIVERY_FAILED_TOPIC = "delivery-failed";

    @KafkaListener(topics = "inventory-updated")
    public void createDelivery(DeliveryCreateEvent event) {
        try {
            log.info("Creating delivery for order: {}", event.getOrderId());

            // Kiểm tra có thể giao hàng đến địa chỉ này không
            boolean canDeliver = validateDeliveryAddress(event.getDeliveryAddress());

            if (canDeliver) {
                // Tạo đơn giao hàng
                Delivery delivery = new Delivery();
                delivery.setOrderId(event.getOrderId());
                delivery.setDeliveryAddress(event.getDeliveryAddress());
                delivery.setStatus(DeliveryStatus.CREATED);
                delivery.setEstimatedDeliveryDate(calculateEstimatedDeliveryDate());

                Delivery savedDelivery = deliveryRepository.save(delivery);

                // Gửi sự kiện giao hàng đã được tạo
                DeliveryCreatedEvent createdEvent = new DeliveryCreatedEvent();
                createdEvent.setSagaId(event.getSagaId());
                createdEvent.setOrderId(event.getOrderId());
                createdEvent.setOrderDTO(event.getOrderDTO());
                createdEvent.setDeliveryId(savedDelivery.getId());
                createdEvent.setEstimatedDeliveryDate(savedDelivery.getEstimatedDeliveryDate());

                kafkaTemplate.send(DELIVERY_CREATED_TOPIC, createdEvent);

                log.info("Delivery created successfully for order: {}", event.getOrderId());
            } else {
                // Gửi sự kiện tạo giao hàng thất bại
                DeliveryFailedEvent failedEvent = new DeliveryFailedEvent();
                failedEvent.setSagaId(event.getSagaId());
                failedEvent.setOrderId(event.getOrderId());
                failedEvent.setOrderDTO(event.getOrderDTO());
                failedEvent.setReason("Cannot deliver to the specified address: " + event.getDeliveryAddress());

                kafkaTemplate.send(DELIVERY_FAILED_TOPIC, failedEvent);

                log.warn("Delivery creation failed for order {}: cannot deliver to address", event.getOrderId());
            }
        } catch (Exception e) {
            log.error("Error creating delivery for order {}: {}", event.getOrderId(), e.getMessage());

            // Gửi sự kiện tạo giao hàng thất bại
            DeliveryFailedEvent failedEvent = new DeliveryFailedEvent();
            failedEvent.setSagaId(event.getSagaId());
            failedEvent.setOrderId(event.getOrderId());
            failedEvent.setOrderDTO(event.getOrderDTO());
            failedEvent.setReason("System error: " + e.getMessage());

            kafkaTemplate.send(DELIVERY_FAILED_TOPIC, failedEvent);
        }
    }

    // API để cập nhật trạng thái giao hàng
    public ShippingDTO updateDeliveryStatus(String deliveryId, ShippingStatus newStatus) {
        Shipping shipping = shippingRepository.findById(deliveryId)
                .orElseThrow(() -> new NotFoundException("Delivery not found: " + deliveryId));

        shipping.setStatus(newStatus);
        if (newStatus == ShippingStatus.SHIPPED) {
            shipping.setDeliveredAt(Instant.now());
        }

        Shipping result = shippingRepository.save(shipping);


        ShippingDTO response = ShippingDTO.builder()
                .id(result.getId())
                .order(result.getOrder())
                .customer(result.getCustomer())
                .shippingAddress(result.getShippingAddress())
                .status(result.getStatus())
                .createdAt(result.getCreatedAt())
                .updatedAt(result.getUpdatedAt())
                .build();

        return response;
    }

    private boolean validateDeliveryAddress(Address address) {
        // Kiểm tra địa chỉ giao hàng có hợp lệ không
        // Trong thực tế, có thể gọi API bên thứ ba để xác nhận địa chỉ

        if (address == null) {
            return false;
        }

        if (address.getStreet() == null || address.getStreet().isEmpty() ||
                address.getCity() == null || address.getCity().isEmpty() ||
                address.getCountry() == null || address.getCountry().isEmpty()) {
            return false;
        }

        // Kiểm tra khu vực có hỗ trợ giao hàng không
        return isDeliverySupported(address.getCity(), address.getCountry());
    }

    private boolean isDeliverySupported(String city, String country) {
        // Trong thực tế, kiểm tra từ cấu hình hoặc database
        // Giả sử chỉ hỗ trợ một số quốc gia/thành phố
        return true; // Giả sử luôn hỗ trợ để demo
    }

    private Date calculateEstimatedDeliveryDate() {
        // Tính toán ngày giao hàng dự kiến
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 3); // Giả sử giao trong 3 ngày
        return calendar.getTime();
    }

}
