package com.congthanh.project.serviceImpl;

import com.congthanh.project.dto.CheckoutDTO;
import com.congthanh.project.dto.PaymentDTO;
import com.congthanh.project.entity.*;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.model.ecommerce.mapper.CheckoutMapper;
import com.congthanh.project.model.ecommerce.request.CreateCheckoutRequest;
import com.congthanh.project.model.ecommerce.request.CreateOrderRequest;
import com.congthanh.project.model.ecommerce.request.CreateOrderDetailRequest;
import com.congthanh.project.repository.cart.CartRepository;
import com.congthanh.project.repository.cartItem.CartItemRepository;
import com.congthanh.project.repository.checkout.CheckoutRepository;
import com.congthanh.project.repository.voucher.VoucherRepository;
import com.congthanh.project.service.CheckoutService;
import com.congthanh.project.service.OrderDetailService;
import com.congthanh.project.service.OrderService;
import com.congthanh.project.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private CheckoutRepository checkoutRepository;

    private CartRepository cartRepository;

    private CartItemRepository cartItemRepository;

    private VoucherRepository voucherRepository;

    private OrderService orderService;

    private OrderDetailService orderDetailService;

    private PaymentService paymentService;

    @Override
    public CheckoutDTO getCheckoutById(int id) {
        Checkout result = checkoutRepository.findById(id).orElseThrow(() -> new NotFoundException("checkout not found"));
        return CheckoutMapper.mapCheckoutEntityToDTO(result);
    }

    @Override
    public CheckoutDTO createCheckout(CreateCheckoutRequest createCheckoutRequest) {
        Cart cart = cartRepository.findById(createCheckoutRequest.getCartId()).orElseThrow(() -> new NotFoundException("cart not found"));
        Voucher voucher = voucherRepository.findById(createCheckoutRequest.getVoucher()).orElseThrow(() -> new NotFoundException("voucher not found"));

        Payment payment = paymentService.createPayment(PaymentDTO.builder()
                .amount(createCheckoutRequest.getTotal())
                .paymentMethod(createCheckoutRequest.getPayment())
                .build());

        Checkout checkout = Checkout.builder()
                .customer(createCheckoutRequest.getCustomer())
                .total(createCheckoutRequest.getTotal())
                .address(createCheckoutRequest.getAddress())
                .payment(payment)
                .checkoutDate(Instant.now().toEpochMilli())
                .phone(createCheckoutRequest.getPhone())
                .cart(cart)
                .voucher(voucher)
                .build();
        Checkout result = checkoutRepository.save(checkout);

        CreateOrderRequest createOrderRequest = CreateOrderRequest.builder()
                .customer(createCheckoutRequest.getCustomer())
//                .total(cart.getTotalOrderPrice())
                .checkout(result.getId())
                .build();
        Order order = orderService.createOrder(createOrderRequest);

        List<CartItem> cartItemList = cartItemRepository.getAllCartItemByCartId(cart.getId());
        for (CartItem cartItem: cartItemList) {
            CreateOrderDetailRequest orderDetailDTO = CreateOrderDetailRequest.builder()
                    .productId(cartItem.getProduct().getId())
                    .quantity(cartItem.getQuantity())
                    .order(order)
                    .build();
            orderDetailService.createOrderDetail(orderDetailDTO);
        }

        cart.setStatus("CHECKED_OUT");
        cartRepository.save(cart);
        return CheckoutMapper.mapCheckoutEntityToDTO(result);
    }
}
