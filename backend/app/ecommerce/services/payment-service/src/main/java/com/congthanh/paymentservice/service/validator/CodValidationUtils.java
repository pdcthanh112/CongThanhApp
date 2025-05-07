package com.congthanh.paymentservice.service.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Component
public class CodValidationUtils {
    private static final BigDecimal MAX_COD_AMOUNT = new BigDecimal("1000");

    public static ValidationResult validateShippingAddress(String address) {
        if (StringUtils.isEmpty(address)) {
            return ValidationResult.error("Shipping address is required for COD");
        }
        return ValidationResult.success();
    }

    public static ValidationResult validateCODAmount(BigDecimal amount) {
        return PaymentValidationUtils.validateAmount(amount, BigDecimal.ONE, MAX_COD_AMOUNT);
    }

    public static ValidationResult validatePhoneNumber(String phoneNumber) {
        if (StringUtils.isEmpty(phoneNumber)) {
            return ValidationResult.error("Phone number is required for COD");
        }

        if (!phoneNumber.matches("^\\+?[0-9]{10,15}$")) {
            return ValidationResult.error("Invalid phone number format");
        }
        return ValidationResult.success();
    }
}
