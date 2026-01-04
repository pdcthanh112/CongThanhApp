package com.congthanh.orderservice.service.serviceImpl;

import com.congthanh.orderservice.exception.NotFoundException;
import com.congthanh.orderservice.grpc.client.ProductGrpcClient;
import com.congthanh.orderservice.grpc.client.PromotionGrpcClient;
import com.congthanh.orderservice.model.entity.OrderStatusTracking;
import com.congthanh.orderservice.model.viewmodel.*;
import com.congthanh.orderservice.repository.orderStatusTracking.OrderStatusTrackingRepository;
import com.congthanh.orderservice.saga.OrderSagaOrchestrator;
import com.congthanh.orderservice.model.dto.OrderDTO;
import com.congthanh.orderservice.model.entity.Order;
import com.congthanh.orderservice.model.request.CreateOrderRequest;
import com.congthanh.orderservice.repository.order.OrderRepository;
import com.congthanh.orderservice.saga.model.OrderSagaExecution;
import com.congthanh.orderservice.service.OrderService;
import com.congthanh.productservice.grpc.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderStatusTrackingRepository orderStatusTrackingRepository;

    private final OrderSagaOrchestrator sagaOrchestrator;

    private final ProductGrpcClient productGrpcClient;

    @Override
    @Transactional
    public OrderDTO createOrder(CreateOrderRequest request) {

        OrderSagaExecution execution = sagaOrchestrator.createOrder(request);

        if(execution.isSuccess()) {
            return  OrderDTO.builder().build();
        }

        throw new RuntimeException(execution.getErrorMessage());
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
                .changedAt(item.getUpdatedAt())
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

//    private BigDecimal calculateTotalAmount(List<OrderItem> items, PromotionResponse promotion) {
//        BigDecimal total = items.stream()
//                .map(item -> item.getOrderPrice().multiply(new BigDecimal(item.getQuantity())))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//        ;
//
//        if (promotion != null) {
//            if (promotion.getType().equals(PromotionType.VOUCHER)) {
//                total = total.subtract(BigDecimal.valueOf(promotion.getValue()));
//            } else if (promotion.getType().equals(PromotionType.DISCOUNT)) {
//                total = total.multiply(BigDecimal.valueOf(promotion.getValue() / 100));
//            }
//        }
//        return total;
//    }
}
