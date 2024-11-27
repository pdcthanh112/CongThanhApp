package com.congthanh.orderservice.service.serviceImpl;

import com.congthanh.orderservice.model.dto.CheckoutDTO;
import com.congthanh.orderservice.model.entity.Checkout;
import com.congthanh.orderservice.model.request.CreateCheckoutRequest;
import com.congthanh.orderservice.service.CheckoutService;
import com.congthanh.orderservice.utils.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutService checkoutService;
    private final SnowflakeIdGenerator snowflakeIdGenerator;

    @Override
    public CheckoutDTO createCheckout(CreateCheckoutRequest request) {
        Checkout checkout = Checkout.builder()
                .id(snowflakeIdGenerator.nextId())
                .build();
        return null;
    }
}
