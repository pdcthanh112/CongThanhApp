package com.congthanh.project.controller;

import com.congthanh.project.constant.enums.PaymentMethod;
import com.congthanh.project.model.request.PaymentRequest;
import com.congthanh.project.model.response.PaymentResponse;
import com.congthanh.project.service.PaymentService;
import com.congthanh.project.utils.Helper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ecommerce/payment")
@RequiredArgsConstructor
@Tag(name = "Payment API", description = "Payment API in CongThanhApp - Ecommerce")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@RequestParam("method") PaymentMethod method, @RequestBody PaymentRequest request, HttpServletRequest httpServletRequest) {
        String cancelUrl = Helper.getBaseURl(httpServletRequest) + "/payment/cancel";
        String successUrl = Helper.getBaseURl(httpServletRequest) + "/payment/success";
        System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCC"+successUrl+"//////////"+cancelUrl);
        PaymentResponse result = paymentService.processPayment(method, request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/paypal/execute")
    public ResponseEntity<PaymentResponse> executePayment(@RequestParam String paymentId, @RequestParam String payerId) {
        Map<String, String> additionalInfo = new HashMap<>();
        additionalInfo.put("paymentId", paymentId);
        additionalInfo.put("payerId", payerId);
        PaymentRequest request = PaymentRequest.builder()
                .additionalInfo(additionalInfo)
                .build();
        PaymentResponse result = paymentService.executePayment(PaymentMethod.PAYPAL, request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/methods")
    public ResponseEntity<List<PaymentMethod>> getAvailablePaymentMethods() {
        return ResponseEntity.ok(Arrays.asList(PaymentMethod.values()));
    }

}
