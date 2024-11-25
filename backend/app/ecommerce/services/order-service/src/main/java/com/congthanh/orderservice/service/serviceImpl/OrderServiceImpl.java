package com.congthanh.orderservice.service.serviceImpl;

import com.congthanh.orderservice.constant.enums.PromotionType;
import com.congthanh.orderservice.model.dto.CheckoutDTO;
import com.congthanh.orderservice.model.dto.OrderDTO;
import com.congthanh.orderservice.model.entity.Checkout;
import com.congthanh.orderservice.model.entity.Order;
import com.congthanh.orderservice.constant.enums.OrderStatus;
import com.congthanh.orderservice.exception.NotFoundException;
import com.congthanh.promotionservice.grpc.GetPromotionByCodeRequest;
import com.congthanh.promotionservice.grpc.PromotionResponse;
import com.congthanh.promotionservice.grpc.PromotionServiceGrpc;
import com.congthanh.orderservice.model.request.CreateOrderRequest;
import com.congthanh.orderservice.model.mapper.OrderMapper;
import com.congthanh.orderservice.model.dto.CartItemResponse;
import com.congthanh.orderservice.model.dto.CartResponse;
import com.congthanh.orderservice.repository.checkout.CheckoutRepository;
import com.congthanh.orderservice.repository.order.OrderRepository;
import com.congthanh.orderservice.service.OrderService;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final CheckoutRepository checkoutRepository;

    private final KafkaTemplate<String, Order> kafkaTemplate;

    @GrpcClient("promotion-service")
    private final PromotionServiceGrpc.PromotionServiceBlockingStub promotionServiceStub ;

    @Override
    @Transactional
    public OrderDTO createOrder(CreateOrderRequest createOrderRequest) {
        Checkout checkout = checkoutRepository.findById((int) createOrderRequest.getCheckout()).orElseThrow(() -> new NotFoundException("Checkout not found"));
        PromotionResponse voucher = promotionServiceStub.getPromotionByCode(GetPromotionByCodeRequest.newBuilder().build());
//        PromotionDTO promotion =
        BigDecimal orderTotal = createOrderRequest.getTotal();
        if(voucher != null) {
            if(voucher.getType().equals(PromotionType.VOUCHER)) {
                orderTotal = checkout.getTotal().subtract(BigDecimal.valueOf(voucher.getValue()));
            } else if (voucher.getType().equals(PromotionType.DISCOUNT)) {
                orderTotal = checkout.getTotal().multiply(BigDecimal.valueOf(voucher.getValue()/100));
            }
        }
        Order order = Order.builder()
                .customer(createOrderRequest.getCustomer())
                .orderDate(Instant.now())
                .checkout(checkout)
                .total(orderTotal)
                .status(OrderStatus.PENDING)
                .build();
        Order result = orderRepository.save(order);

        kafkaTemplate.send("order-created-topic", result);

        return OrderMapper.mapOrderEntityToDTO(result);
    }

    @Override
    public List<CheckoutDTO> getHistoryByCustomer(String customerId) {

        List<CheckoutDTO> response = new ArrayList<>();
        List<Tuple> listCheckout = orderRepository.getHistoryByCustomer(customerId);
        if (listCheckout.size() > 0) {
            CheckoutDTO checkoutDTO = new CheckoutDTO();
            for (Tuple checkout : listCheckout) {
                checkoutDTO.setId(checkout.get("checkoutId", Long.class));
                checkoutDTO.setCheckoutDate(checkout.get("checkout_date", Instant.class));
                checkoutDTO.setAddress(checkout.get("address", String.class));
                checkoutDTO.setPhone(checkout.get("phone", String.class));
                checkoutDTO.setPaymentMethod(checkout.get("payment_method", String.class));
                checkoutDTO.setTotal(checkout.get("total", BigDecimal.class));

                CartResponse cart = null;
                //getAllCartItemByCartId
                List<CartItemResponse> listCartItem = null;
                if (listCartItem.size() > 0) {
                    Set<CartItemResponse> cartItems = new HashSet<>();
                    for (CartItemResponse cartItemItem : listCartItem) {
                        CartItemResponse cartItemTmp = new CartItemResponse();
                        cartItemTmp.setId(cartItemItem.getId());
                        cartItemTmp.setQuantity(cartItemItem.getQuantity());
                        cartItemTmp.setCart(cart);
                        cartItemTmp.setProduct(cartItemItem.getProduct());
                        cartItems.add(cartItemTmp);
                    }
                    cart.setCartItems(cartItems);
                }
                checkoutDTO.setCart(cart);
                response.add(checkoutDTO);
            }
        } else {
            return null;
        }
        return response;
    }

    @KafkaListener(topics = "shipment-completed-topic")
    public void handleShipmentCompleted(ShippingEvent shippingEvent) {
        // Cập nhật trạng thái đơn hàng thành "Shipped"
        Order order = orderRepository.findById(shippingEvent.eventType);
        order.setStatus(OrderStatus.SHIPPED);
        Order result = orderRepository.save(order);

        // Đẩy sự kiện OrderStatusUpdated lên Kafka
        OrderEvent orderEvent = new OrderEvent("UPDATE", OrderMapper.mapOrderEntityToDTO(result));
        kafkaTemplate.send("order-topic", orderEvent);
    }
}
