package com.congthanh.paymentservice.controller;

import com.congthanh.paymentservice.model.request.PaymentRequest;
import com.congthanh.paymentservice.model.response.PaymentVNPayResponse;
import com.congthanh.paymentservice.service.VNPayConfig;
import com.congthanh.paymentservice.service.strategy.VNPayPaymentStrategy;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ecommerce/payment/vnpay")
@RequiredArgsConstructor
@Slf4j
public class VNPayPaymentController {

    private final VNPayPaymentStrategy vnPayService;

    @PostMapping("/create")
    public ResponseEntity<PaymentVNPayResponse> createPayment(@RequestBody PaymentRequest request, HttpServletRequest httpServletRequest) {
        String ipAddress = VNPayConfig.getIpAddress(httpServletRequest);
        PaymentVNPayResponse response = vnPayService.processPayment(request, ipAddress);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ipn")
    public ResponseEntity<String> paymentCallback(@RequestParam Map<String, String> params) {
        String vnp_SecureHash = params.get("vnp_SecureHash");
        params.remove("vnp_SecureHash");

        boolean isValid = vnPayService.verifyPayment(params, vnp_SecureHash);
        if (isValid && "00".equals(params.get("vnp_TransactionStatus"))) {
            vnPayService.executePayment(params.get("payment_id"));
            return ResponseEntity.ok("Payment successful");
        } else {
            return ResponseEntity.badRequest().body("Payment failed or invalid signature");
        }
    }

}
