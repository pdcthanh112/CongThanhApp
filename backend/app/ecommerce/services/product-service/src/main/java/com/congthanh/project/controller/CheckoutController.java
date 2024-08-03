package com.congthanh.project.controller;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.CheckoutDTO;
import com.congthanh.project.model.ecommerce.request.CreateCheckoutRequest;
import com.congthanh.project.model.ecommerce.response.Response;
import com.congthanh.project.service.CheckoutService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ecommerce/checkout")
@Tag(name = "Checkout API", description = "Checkout API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class CheckoutController {

    private CheckoutService checkoutService;

    @GetMapping("/{id}")
    public ResponseEntity<Response<CheckoutDTO>> getCheckoutById(@PathVariable("id") int checkoutId) {
        CheckoutDTO data = checkoutService.getCheckoutById(checkoutId);
        Response<CheckoutDTO> response = new Response<>();
        response.setData(data);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("Get checkout successfully");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/")
    public ResponseEntity<Response<CheckoutDTO>> createCheckout(@RequestBody CreateCheckoutRequest createCheckoutRequest) {
        CheckoutDTO checkout = checkoutService.createCheckout(createCheckoutRequest);
        Response<CheckoutDTO> response = new Response<>();
        response.setData(checkout);
        response.setStatus(ResponseStatus.STATUS_SUCCESS);
        response.setMessage("created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}
