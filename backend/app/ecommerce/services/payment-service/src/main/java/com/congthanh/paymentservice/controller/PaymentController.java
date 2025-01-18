package com.congthanh.paymentservice.controller;

import com.congthanh.paymentservice.constant.enums.PaymentMethod;
import com.congthanh.paymentservice.constant.enums.TransactionStatus;
import com.congthanh.paymentservice.model.request.PaymentRequest;
import com.congthanh.paymentservice.model.response.PaymentResponse;
import com.congthanh.paymentservice.service.PaymentService;
import com.congthanh.paymentservice.service.PaymentTransactionService;
import com.congthanh.paymentservice.utils.Helper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    private final PaymentTransactionService transactionService;

    @Operation(
            summary = "Initialize payment",
            description = "Creates a new payment transaction and returns necessary data for payment processing"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment initialized successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request parameters"
            )
    })
    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@RequestParam("method") PaymentMethod method, @RequestBody PaymentRequest request, HttpServletRequest httpServletRequest) {
        String cancelUrl = Helper.getBaseURl(httpServletRequest) + "/payment/cancel";
        String successUrl = Helper.getBaseURl(httpServletRequest) + "/payment/success";
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

    @GetMapping("/{transactionId}/status")
    public ResponseEntity<TransactionStatus> getPaymentStatus(@PathVariable("transactionId") Long transactionId) {
        return ResponseEntity.ok(transactionService.getTransactionStatus(transactionId));
    }

    @GetMapping("/methods")
    public ResponseEntity<List<PaymentMethod>> getAvailablePaymentMethods() {
        return ResponseEntity.ok(Arrays.asList(PaymentMethod.values()));
    }

}
