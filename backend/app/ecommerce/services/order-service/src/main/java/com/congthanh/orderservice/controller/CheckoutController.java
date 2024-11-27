package com.congthanh.orderservice.controller;

import com.congthanh.orderservice.model.dto.CheckoutDTO;
import com.congthanh.orderservice.model.request.CreateCheckoutRequest;
import com.congthanh.orderservice.service.CheckoutService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ecommerce/checkout")
@Tag(name = "Checkout API", description = "Checkout API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping("/")
    public ResponseEntity<CheckoutDTO> createCheckout(@RequestBody @Valid CreateCheckoutRequest request) {
        return ResponseEntity.ok(checkoutService.createCheckout(request));
    }

}
