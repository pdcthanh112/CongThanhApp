package com.congthanh.project.serviceImpl;

import com.congthanh.project.dto.*;
import com.congthanh.project.entity.Checkout;
import com.congthanh.project.entity.Order;
import com.congthanh.project.enums.ecommerce.OrderStatus;
import com.congthanh.project.exception.NotFoundException;
import com.congthanh.project.model.ecommerce.request.CreateOrderRequest;
import com.congthanh.project.model.response.CartItemResponse;
import com.congthanh.project.model.response.CartResponse;
import com.congthanh.project.model.response.VoucherResponse;
import com.congthanh.project.repository.checkout.CheckoutRepository;
import com.congthanh.project.repository.order.OrderRepository;
import com.congthanh.project.service.OrderService;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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

    private final WebClient webClient;

    @Override
    public Order createOrder(CreateOrderRequest createOrderRequest) {
        Checkout checkout = checkoutRepository.findById((int) createOrderRequest.getCheckout()).orElseThrow(() -> new NotFoundException("Checkout not found"));
        VoucherResponse voucher = webClient.get().uri("/voucher/{id}", createOrderRequest.getCheckout()).retrieve().bodyToMono(VoucherResponse.class).block();
        BigDecimal orderTotal = createOrderRequest.getTotal();
        if(voucher != null) {
            if(voucher.getType().equals("PROMOTION")) {
                orderTotal = checkout.getTotal().subtract(BigDecimal.valueOf(voucher.getValue()));
            } else if (voucher.getType().equals("DISCOUNT")) {
                orderTotal = checkout.getTotal().multiply(BigDecimal.valueOf(voucher.getValue()/100));
            }
        }
        Order order = Order.builder()
                .customer(createOrderRequest.getCustomer())
                .orderDate(Instant.now().toEpochMilli())
                .checkout(checkout.getId())
                .total(orderTotal)
                .status(OrderStatus.PENDING)
                .build();
        return orderRepository.save(order);
    }

    @Override
    public List<CheckoutDTO> getHistoryByCustomer(String customerId) {

        List<CheckoutDTO> response = new ArrayList<>();
        List<Tuple> listCheckout = orderRepository.getHistoryByCustomer(customerId);
        if (listCheckout.size() > 0) {
            CheckoutDTO checkoutDTO = new CheckoutDTO();
            for (Tuple checkout : listCheckout) {
                checkoutDTO.setId(checkout.get("checkoutId", Long.class));
                checkoutDTO.setCheckoutDate(checkout.get("checkout_date", Long.class));
                checkoutDTO.setAddress(checkout.get("address", String.class));
                checkoutDTO.setPhone(checkout.get("phone", String.class));
                checkoutDTO.setPaymentMethod(checkout.get("payment_method", String.class));
                checkoutDTO.setTotal(checkout.get("total", BigDecimal.class));

                CartResponse cart = webClient.get().uri("/cart/{id}", checkout.get("cartId", String.class)).retrieve().bodyToMono(CartResponse.class).block();

                List<CartItemResponse> listCartItem = cartItemRepository.getAllCartItemByCartId(cart.getId());
                if (listCartItem.size() > 0) {
                    Set<CartItemResponse> cartItems = new HashSet<>();
                    for (CartItem cartItemItem : listCartItem) {
                        CartItemDTO cartItemTmp = new CartItemDTO();
                        cartItemTmp.setId(cartItemItem.getId());
                        cartItemTmp.setQuantity(cartItemItem.getQuantity());
                        cartItemTmp.setCart(CartMapper.mapCartEntityToDTO(cart));
                        cartItemTmp.setProduct(ProductMapper.mapProductEntityToDTO(cartItemItem.getProduct()));
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
}
