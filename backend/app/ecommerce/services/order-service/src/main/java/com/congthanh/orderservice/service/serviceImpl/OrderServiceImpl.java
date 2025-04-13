package com.congthanh.orderservice.service.serviceImpl;

import com.congthanh.orderservice.constant.enums.PromotionType;
import com.congthanh.orderservice.exception.NotFoundException;
import com.congthanh.orderservice.grpc.client.PromotionGrpcClient;
import com.congthanh.orderservice.model.viewmodel.OrderVm;
import com.congthanh.orderservice.saga.OrderSagaOrchestrator;
import com.congthanh.orderservice.model.dto.OrderDTO;
import com.congthanh.orderservice.model.entity.Order;
import com.congthanh.orderservice.constant.enums.OrderStatus;
import com.congthanh.orderservice.model.entity.OrderItem;
import com.congthanh.orderservice.model.request.CreateOrderRequest;
import com.congthanh.orderservice.model.mapper.OrderMapper;
import com.congthanh.orderservice.repository.order.OrderRepository;
import com.congthanh.orderservice.service.OrderService;
import com.congthanh.orderservice.utils.Helper;
import com.congthanh.promotionservice.grpc.PromotionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderSagaOrchestrator sagaOrchestrator;

    private final PromotionGrpcClient promotionGrpcClient;

    @Override
    @Transactional
    public OrderDTO createOrder(CreateOrderRequest request) {
        validateOrder(request);

        PromotionResponse promotion = null;
        if(request.getPromotionCode() != null){
            promotion = promotionGrpcClient.getPromotionByCode(request.getPromotionCode());
        }

        BigDecimal orderTotal = calculateTotalAmount(request.getOrderItems(), promotion);

        Order order = Order.builder()
                .orderCode(Helper.generateOrderNumber())
                .customer(request.getCustomer())
                .orderDate(Instant.now())
                .totalAmount(orderTotal)
                .status(OrderStatus.PENDING)
                .build();
        Order result = orderRepository.save(order);

        sagaOrchestrator.startOrderSaga(request);

        return OrderMapper.mapOrderEntityToDTO(result);
    }

    @Override
    public OrderVm getOrderByCode(String orderCode) {
        Order order = orderRepository.findByOrderCode(orderCode).orElseThrow(() -> new NotFoundException("Order not found"));
        return OrderVm.builder()
                .id(order.getId())
                .orderNumber(order.getOrderCode())
                .customer(order.getCustomer())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .paymentStatus(order.getPaymentStatus())
                .shippingStatus(order.getShippingStatus())
                .build();
    }

    private void validateOrder(CreateOrderRequest orderRequest) {
        if (orderRequest.getCustomer() == null || orderRequest.getCustomer().isEmpty()) {
            throw new RuntimeException("Customer ID is required");
        }

        if (orderRequest.getOrderItems() == null || orderRequest.getOrderItems().isEmpty()) {
            throw new RuntimeException("Order must contain at least one item");
        }

//        if (orderRequest.getDeliveryAddress() == null) {
//            throw new RuntimeException("Delivery address is required");
//        }
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> items, PromotionResponse promotion) {
        BigDecimal total = items.stream()
                .map(item -> item.getOrderPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);;

        if(promotion != null) {
            if(promotion.getType().equals(PromotionType.VOUCHER)) {
                total = total.subtract(BigDecimal.valueOf(promotion.getValue()));
            } else if (promotion.getType().equals(PromotionType.DISCOUNT)) {
                total = total.multiply(BigDecimal.valueOf(promotion.getValue()/100));
            }
        }
        return total;
    }
}
