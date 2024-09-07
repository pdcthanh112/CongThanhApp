package com.congthanh.project.controller;

import com.congthanh.project.cqrs.command.command.ProcessPaymentCommand;
import com.congthanh.project.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/ecommerce/payment")
@Tag(name = "Payment API", description = "Payment API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    private final CommandGateway commandGateway;

    @PostMapping("/process")
    public CompletableFuture<String> processPayment(@RequestBody ProcessPaymentCommand command) {
        return commandGateway.send(new ProcessPaymentCommand(UUID.randomUUID().toString(), command.getOrderId(), command.getAmount()));
    }

}
