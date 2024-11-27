package com.congthanh.orderservice.service;

import com.congthanh.orderservice.model.dto.CheckoutDTO;
import com.congthanh.orderservice.model.request.CreateCheckoutRequest;

public interface CheckoutService {

    CheckoutDTO createCheckout(CreateCheckoutRequest request);

}
