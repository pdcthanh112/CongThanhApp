package com.congthanh.project.service.ecommerce;

import com.congthanh.project.dto.CheckoutDTO;
import com.congthanh.project.model.ecommerce.request.CreateCheckoutRequest;

public interface CheckoutService {


    CheckoutDTO getCheckoutById(int id);
    CheckoutDTO createCheckout(CreateCheckoutRequest createCheckoutRequest);

}
