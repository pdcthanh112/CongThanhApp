package com.congthanh.project.serviceImpl.ecommerce;

import com.congthanh.project.dto.ecommerce.CheckoutDTO;
import com.congthanh.project.dto.ecommerce.PaymentDTO;
import com.congthanh.project.entity.ecommerce.*;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.model.ecommerce.mapper.CheckoutMapper;
import com.congthanh.project.model.ecommerce.request.CreateCheckoutRequest;
import com.congthanh.project.model.ecommerce.request.CreateOrderRequest;
import com.congthanh.project.model.ecommerce.request.CreateOrderDetailRequest;
import com.congthanh.project.repository.ecommerce.cart.CartRepository;
import com.congthanh.project.repository.ecommerce.cartItem.CartItemRepository;
import com.congthanh.project.repository.ecommerce.checkout.CheckoutRepository;
import com.congthanh.project.repository.ecommerce.voucher.VoucherRepository;
import com.congthanh.project.service.ecommerce.CheckoutService;
import com.congthanh.project.service.ecommerce.OrderDetailService;
import com.congthanh.project.service.ecommerce.OrderService;
import com.congthanh.project.service.ecommerce.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CheckoutMapper checkoutMapper;

    @Override
    public CheckoutDTO getCheckoutById(int id) {
        Checkout result = checkoutRepository.findById(id).orElseThrow(() -> new NotFoundException("checkout not found"));
        return checkoutMapper.mapCheckoutEntityToDTO(result);
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
        return checkoutMapper.mapCheckoutEntityToDTO(result);
    }
}
