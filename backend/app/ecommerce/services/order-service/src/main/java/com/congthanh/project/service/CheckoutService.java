package com.congthanh.project.service;

import com.congthanh.project.dto.ecommerce.CheckoutDTO;
import com.congthanh.project.model.ecommerce.request.CreateCheckoutRequest;

public interface CheckoutService {


    CheckoutDTO getCheckoutById(int id);
    CheckoutDTO createCheckout(CreateCheckoutRequest createCheckoutRequest);

}
