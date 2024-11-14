package com.congthanh.project.service.validator;

import com.congthanh.project.model.request.PaymentRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Component
public class PaymentValidationUtils {

    public static ValidationResult validateCommonFields(PaymentRequest request) {
        if (request == null) {
            return ValidationResult.error("Payment request cannot be null");
        }

        if (StringUtils.isEmpty(request.getOrderId())) {
            return ValidationResult.error("Order ID is required");
        }

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return ValidationResult.error("Invalid payment amount");
        }

        if (StringUtils.isEmpty(request.getCurrency())) {
            return ValidationResult.error("Currency is required");
        }

        return ValidationResult.success();
    }

    public static ValidationResult validateAmount(BigDecimal amount, BigDecimal minAmount, BigDecimal maxAmount) {
        if (amount.compareTo(minAmount) < 0) {
            return ValidationResult.error("Amount is below minimum limit: " + minAmount);
        }

        if (amount.compareTo(maxAmount) > 0) {
            return ValidationResult.error("Amount exceeds maximum limit: " + maxAmount);
        }

        return ValidationResult.success();
    }

}
