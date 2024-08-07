package com.congthanh.project.service;

import com.congthanh.project.dto.CheckoutDTO;
import com.congthanh.project.model.request.CreateCheckoutRequest;

public interface CheckoutService {


    CheckoutDTO getCheckoutById(int id);
    CheckoutDTO createCheckout(CreateCheckoutRequest createCheckoutRequest);

}
