package com.congthanh.project.controller;

import com.congthanh.project.service.WebhookProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ecommerce/payment/webhooks")
@RequiredArgsConstructor
public class PaymentWebhookController {

    private final WebhookProcessingService webhookService;

    @PostMapping("/stripe")
    public ResponseEntity<String> handleStripeWebhook(@RequestHeader("Stripe-Signature") String signature, @RequestBody String payload) {
        return ResponseEntity.ok(webhookService.processStripeWebhook(signature, payload));
    }

    @PostMapping("/paypal")
    public ResponseEntity<String> handlePayPalWebhook(@RequestHeader("Paypal-Signature") String signature, @RequestBody String payload) {
        return ResponseEntity.ok(webhookService.processPaypalWebhook(signature, payload));
    }


}
