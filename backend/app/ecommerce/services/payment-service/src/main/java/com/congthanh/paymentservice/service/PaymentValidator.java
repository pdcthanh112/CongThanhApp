package com.congthanh.paymentservice.service;

import com.congthanh.paymentservice.constant.enums.PaymentMethod;
import com.congthanh.paymentservice.exception.ecommerce.PaymentValidationException;
import com.congthanh.paymentservice.model.request.PaymentRequest;
import com.congthanh.paymentservice.service.validator.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PaymentValidator {

    public void validate(PaymentRequest request) {
        // Validate common fields first
        ValidationResult commonValidation = PaymentValidationUtils.validateCommonFields(request);
        if (!commonValidation.isValid()) {
            throw new PaymentValidationException(commonValidation.getErrorMessage());
        }

        // Validate based on payment method
        ValidationResult methodValidation = validateByMethod(request);
        if (!methodValidation.isValid()) {
            throw new PaymentValidationException(methodValidation.getErrorMessage());
        }
    }

    private ValidationResult validateByMethod(PaymentRequest request) {
        switch (request.getPaymentMethod()) {
            case PaymentMethod.CREDIT_CARD:
                return validateCreditCard(request);
            case PAYPAL:
                return validatePayPal(request);
            case PaymentMethod.CASH_ON_DELIVERY:
                return validateCOD(request);
            default:
                return ValidationResult.error("Unsupported payment method");
        }
    }

    private ValidationResult validateCreditCard(PaymentRequest request) {
        Map<String, String> cardData = request.getAdditionalInfo();

        ValidationResult cardFields = CreditCardValidationUtils.validateCardFields(cardData);
        if (!cardFields.isValid()) return cardFields;

        ValidationResult cardNumber = CreditCardValidationUtils.validateCardNumber(cardData.get("cardNumber"));
        if (!cardNumber.isValid()) return cardNumber;

        ValidationResult cvv = CreditCardValidationUtils.validateCVV(cardData.get("cvv"));
        if (!cvv.isValid()) return cvv;

        return CreditCardValidationUtils.validateExpiryDate(
                cardData.get("expiryMonth"),
                cardData.get("expiryYear")
        );
    }

    private ValidationResult validatePayPal(PaymentRequest request) {
        ValidationResult currency = PayPalValidationUtils.validateCurrency(request.getCurrency());
        if (!currency.isValid()) return currency;

        ValidationResult amount = PayPalValidationUtils.validatePayPalAmount(request.getAmount());
        if (!amount.isValid()) return amount;

        String returnUrl = request.getAdditionalInfo().get("returnUrl");
        if (returnUrl != null) {
            return PayPalValidationUtils.validateReturnUrl(returnUrl);
        }

        String cancelUrl = request.getAdditionalInfo().get("returnUrl");
        if (cancelUrl != null) {
            return PayPalValidationUtils.validateReturnUrl(cancelUrl);
        }

        return ValidationResult.success();
    }

    private ValidationResult validateCOD(PaymentRequest request) {
        Map<String, String> data = request.getAdditionalInfo();

        ValidationResult address = CodValidationUtils.validateShippingAddress(data.get("shippingAddress"));
        if (!address.isValid()) return address;

        ValidationResult amount = CodValidationUtils.validateCODAmount(request.getAmount());
        if (!amount.isValid()) return amount;

        return CodValidationUtils.validatePhoneNumber(data.get("phoneNumber"));
    }

}
