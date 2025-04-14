package com.congthanh.orderservice.service.serviceImpl;

import com.congthanh.orderservice.constant.enums.PromotionType;
import com.congthanh.orderservice.exception.NotFoundException;
import com.congthanh.orderservice.grpc.client.ProductGrpcClient;
import com.congthanh.orderservice.grpc.client.PromotionGrpcClient;
import com.congthanh.orderservice.model.entity.OrderStatusTracking;
import com.congthanh.orderservice.model.viewmodel.*;
import com.congthanh.orderservice.repository.orderStatusTracking.OrderStatusTrackingRepository;
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
import com.congthanh.productservice.grpc.ProductResponse;
import com.congthanh.promotionservice.grpc.PromotionResponse;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderStatusTrackingRepository orderStatusTrackingRepository;

    private final OrderSagaOrchestrator sagaOrchestrator;

    private final PromotionGrpcClient promotionGrpcClient;

    private final QueryGateway queryGateway;

    private final ProductGrpcClient productGrpcClient;

    @Override
    @Transactional
    public OrderDTO createOrder(CreateOrderRequest request) {
        validateOrder(request);

        PromotionResponse promotion = null;
        if (request.getPromotionCode() != null) {
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
    public OrderDetailVm getOrderDetailByCode(String orderCode) {
        Order order = orderRepository.findByOrderCode(orderCode).orElseThrow(() -> new NotFoundException("Order not found"));

        List<OrderStatusTracking> statusTracking = orderStatusTrackingRepository.getStatusTrackingByOrderCode(orderCode);

        List<OrderStatusTrackingVm> statusTrackingVms = statusTracking.stream().map(item -> OrderStatusTrackingVm.builder()
                .id(item.getId())
                .status(item.getStatus())
                .stepOrder(item.getStepOrder())
                .description(item.getDescription())
                .changedAt(item.getChangedAt())
                .build()).toList();

        return OrderDetailVm.builder()
                .id(order.getId())
                .orderNumber(order.getOrderCode())
                .customer(order.getCustomer())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .paymentStatus(order.getPaymentStatus())
                .shippingStatus(order.getShippingStatus())
                .orderStatusTracking(statusTrackingVms)
                .build();
    }

    @Override
    public List<OrderHistoryVm> getOrderHistoryByCustomer(String customerId) {
        List<Order> data = orderRepository.getOrderHistoryByCustomer(customerId);
        if (data.isEmpty()) {
            return List.of();
        }
        List<OrderHistoryVm> orderVms = new ArrayList<>();
        for (Order order : data) {
            OrderHistoryVm vm = OrderHistoryVm.builder()
                    .id(order.getId())
                    .customer(order.getCustomer())
                    .orderDate(order.getOrderDate())
                    .status(order.getStatus())
                    .paymentStatus(order.getPaymentStatus())
                    .shippingStatus(order.getShippingStatus())
                    .orderItems(!order.getOrderItem().isEmpty() ? order.getOrderItem().stream().map(item -> {
                        ProductResponse productResponse = productGrpcClient.getProductById(item.getProduct());
                        OrderItemHistory.ProductVm productVm = new OrderItemHistory.ProductVm(
                                productResponse.getId(),
                                productResponse.getName(),
                                productResponse.getSlug(),
                                productResponse.getName()
                        );
                        return OrderItemHistory.builder().product(productVm).build();
                    }).toList() : List.of())
                    .build();
            orderVms.add(vm);
        }
        return orderVms;
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
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        ;

        if (promotion != null) {
            if (promotion.getType().equals(PromotionType.VOUCHER)) {
                total = total.subtract(BigDecimal.valueOf(promotion.getValue()));
            } else if (promotion.getType().equals(PromotionType.DISCOUNT)) {
                total = total.multiply(BigDecimal.valueOf(promotion.getValue() / 100));
            }
        }
        return total;
    }
}
