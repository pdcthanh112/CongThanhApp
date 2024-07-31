package com.congthanh.project.serviceImpl.ecommerce;

import com.congthanh.project.dto.ecommerce.*;
import com.congthanh.project.entity.ecommerce.*;
import com.congthanh.project.enums.ecommerce.OrderStatus;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.model.ecommerce.mapper.CartMapper;
import com.congthanh.project.model.ecommerce.mapper.ProductMapper;
import com.congthanh.project.model.ecommerce.request.CreateOrderRequest;
import com.congthanh.project.repository.cartItem.CartItemRepository;
import com.congthanh.project.repository.cart.CartRepository;
import com.congthanh.project.repository.checkout.CheckoutRepository;
import com.congthanh.project.repository.order.OrderRepository;
import com.congthanh.project.repository.voucher.VoucherRepository;
import com.congthanh.project.service.ecommerce.OrderService;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final OrderRepository orderRepository;

    private final CheckoutRepository checkoutRepository;

    private final VoucherRepository voucherRepository;

    @Override
    public Order createOrder(CreateOrderRequest createOrderRequest) {
        Checkout checkout = checkoutRepository.findById((int) createOrderRequest.getCheckout()).orElseThrow(() -> new NotFoundException("Checkout not found"));
        Voucher voucher = voucherRepository.findById(checkout.getVoucher().getId()).orElseThrow(() -> new NotFoundException("voucher not found"));
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
                .checkout(checkout)
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

                Cart cart = cartRepository.findById(checkout.get("cartId", String.class)).orElseThrow();

                CartDTO cartTmp = new CartDTO();
                cartTmp.setId(cart.getId());
                cartTmp.setName(cart.getName());
                cartTmp.setCustomer(cart.getCustomer());
                cartTmp.setStatus(cart.getStatus());
                cartTmp.setCreatedDate(cart.getCreatedAt());
                List<CartItem> listCartItem = cartItemRepository.getAllCartItemByCartId(cart.getId());
                if (listCartItem.size() > 0) {
                    Set<CartItemDTO> cartItems = new HashSet<>();
                    for (CartItem cartItemItem : listCartItem) {
                        CartItemDTO cartItemTmp = new CartItemDTO();
                        cartItemTmp.setId(cartItemItem.getId());
                        cartItemTmp.setQuantity(cartItemItem.getQuantity());
                        cartItemTmp.setCart(CartMapper.mapCartEntityToDTO(cart));
                        cartItemTmp.setProduct(ProductMapper.mapProductEntityToDTO(cartItemItem.getProduct()));
                        cartItems.add(cartItemTmp);
                    }
                    cartTmp.setCartItems(cartItems);
                }
                checkoutDTO.setCart(cartTmp);
                response.add(checkoutDTO);
            }
        } else {
            return null;
        }
        return response;
    }
}
