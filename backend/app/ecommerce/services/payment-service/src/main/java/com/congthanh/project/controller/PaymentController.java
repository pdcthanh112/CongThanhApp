package com.congthanh.project.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ecommerce/payment")
@Tag(name = "Payment API", description = "Payment API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class PaymentController {
}
