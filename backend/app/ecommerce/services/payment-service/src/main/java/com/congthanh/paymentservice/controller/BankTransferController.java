package com.congthanh.paymentservice.controller;

import com.congthanh.paymentservice.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ecommerce/payment")
@RequiredArgsConstructor
@Tag(name = "Payment API", description = "Payment API in CongThanhApp - Ecommerce")
public class BankTransferController {

    private final PaymentService paymentService;

    @GetMapping("/banks")
    public ResponseEntity<List<BankInfo>> getSupportedBanks() {
        return ResponseEntity.ok(bankTransferService.getSupportedBanks());
    }

    @PostMapping("/virtual-account")
    public ResponseEntity<VirtualAccountResponse> createVirtualAccount(
            @Valid @RequestBody VirtualAccountRequest request
    ) {
        return ResponseEntity.ok(
                bankTransferService.createVirtualAccount(request)
        );
    }

}
