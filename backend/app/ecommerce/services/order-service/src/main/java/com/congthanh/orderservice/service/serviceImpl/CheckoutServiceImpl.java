package com.congthanh.orderservice.service.serviceImpl;

import com.congthanh.orderservice.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutService checkoutService;
}
