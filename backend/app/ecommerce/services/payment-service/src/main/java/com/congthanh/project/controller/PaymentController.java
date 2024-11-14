package com.congthanh.project.controller;

import com.congthanh.project.constant.enums.PaymentMethod;
import com.congthanh.project.model.request.PaymentRequest;
import com.congthanh.project.model.response.PaymentResponse;
import com.congthanh.project.service.PaymentService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ecommerce/payment")
@RequiredArgsConstructor
@Tag(name = "Payment API", description = "Payment API in CongThanhApp - Ecommerce")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@RequestParam("method") PaymentMethod method, @RequestBody PaymentRequest request) {
        PaymentResponse result = paymentService.processPayment(method, request);
        System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCC"+result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/paypal")
    public ResponseEntity<?> testpaypal(@RequestBody PaymentRequest request) {
//        try {
            PaymentResponse payment = paymentService.processPayment(PaymentMethod.PAYPAL, request);

//            for (Links links : payment.getLinks()) {
//                if (links.getRel().equals("approval_url")) {
//                    return ResponseEntity.ok("redirect:" + links.getHref());
//                }
//            }
//        } catch (PayPalRESTException e) {
//            System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEE" + e.getMessage());
//        }
//        System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCC"+payment);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/methods")
    public ResponseEntity<List<PaymentMethod>> getAvailablePaymentMethods() {
        return ResponseEntity.ok(Arrays.asList(PaymentMethod.values()));
    }

}
