package com.congthanh.project.service.validator;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Set;

@Component
public class PayPalValidationUtils {

    private static final Set<String> SUPPORTED_CURRENCIES = Set.of("USD", "EUR", "GBP");
    private static final BigDecimal MAX_AMOUNT = new BigDecimal("10000");

    public static ValidationResult validateCurrency(String currency) {
        if (!SUPPORTED_CURRENCIES.contains(currency)) {
            return ValidationResult.error("Currency not supported by PayPal: " + currency);
        }
        return ValidationResult.success();
    }

    public static ValidationResult validatePayPalAmount(BigDecimal amount) {
        return PaymentValidationUtils.validateAmount(amount, BigDecimal.ONE, MAX_AMOUNT);
    }

    public static ValidationResult validateReturnUrl(String url) {
        try {
            new URL(url).toURI();
            return ValidationResult.success();
        } catch (Exception e) {
            return ValidationResult.error("Invalid return URL");
        }
    }

}
