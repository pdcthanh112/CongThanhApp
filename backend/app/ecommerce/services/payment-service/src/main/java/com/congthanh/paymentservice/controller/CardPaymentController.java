package com.congthanh.paymentservice.controller;

import com.congthanh.paymentservice.model.request.CardTokenizeRequest;
import com.congthanh.paymentservice.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ecommerce/payment/card")
@RequiredArgsConstructor
@Tag(name = "Payment API", description = "Payment API in CongThanhApp - Ecommerce")
public class CardPaymentController {

    private final PaymentService paymentService;

//    @PostMapping("/tokenize")
//    public ResponseEntity<TokenizeResponse> tokenizeCard(@RequestBody @Valid CardTokenizeRequest request) {
//        return ResponseEntity.ok(cardPaymentService.tokenizeCard(request));
//    }


}
