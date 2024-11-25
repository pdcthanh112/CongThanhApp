package com.congthanh.paymentservice.service.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Map;

@Component
public class CreditCardValidationUtils {
    public static ValidationResult validateCardFields(Map<String, String> cardData) {
        String cardNumber = cardData.get("cardNumber");
        String cvv = cardData.get("cvv");
        String expiryMonth = cardData.get("expiryMonth");
        String expiryYear = cardData.get("expiryYear");

        if (StringUtils.isEmpty(cardNumber)) {
            return ValidationResult.error("Card number is required");
        }

        if (StringUtils.isEmpty(cvv)) {
            return ValidationResult.error("CVV is required");
        }

        if (StringUtils.isEmpty(expiryMonth) || StringUtils.isEmpty(expiryYear)) {
            return ValidationResult.error("Card expiry date is required");
        }

        return ValidationResult.success();
    }
    public static ValidationResult validateCardNumber(String cardNumber) {
        cardNumber = cardNumber.replaceAll("\\s+|-", "");

        if (!cardNumber.matches("\\d+")) {
            return ValidationResult.error("Card number must contain only digits");
        }

        if (!isValidLuhn(cardNumber)) {
            return ValidationResult.error("Invalid card number");
        }

        return ValidationResult.success();
    }

    public static ValidationResult validateCVV(String cvv) {
        if (!cvv.matches("^[0-9]{3,4}$")) {
            return ValidationResult.error("Invalid CVV format");
        }
        return ValidationResult.success();
    }

    public static ValidationResult validateExpiryDate(String month, String year) {
        try {
            int expiryMonth = Integer.parseInt(month);
            int expiryYear = Integer.parseInt(year);

            LocalDate now = LocalDate.now();
            int currentYear = now.getYear();
            int currentMonth = now.getMonthValue();

            if (expiryYear < 100) {
                expiryYear += 2000;
            }

            if (expiryYear < currentYear ||
                    (expiryYear == currentYear && expiryMonth < currentMonth)) {
                return ValidationResult.error("Card has expired");
            }

            if (expiryMonth < 1 || expiryMonth > 12) {
                return ValidationResult.error("Invalid expiry month");
            }

            return ValidationResult.success();
        } catch (NumberFormatException e) {
            return ValidationResult.error("Invalid expiry date format");
        }
    }

    private static boolean isValidLuhn(String cardNumber) {
        // Luhn algorithm implementation
        // ... (giữ nguyên code cũ)
        return true;
    }
}
