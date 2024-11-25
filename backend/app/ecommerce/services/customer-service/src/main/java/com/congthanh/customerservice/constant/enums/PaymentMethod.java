package com.congthanh.customerservice.constant.enums;

public enum PaymentMethod {
    CASH_ON_DELIVERY("COD"),
    CREDIT_DEBIT_CARD("Credit/Debit Card"),
    E_WALLET("E-Wallet"),
    BANK_TRANSFER("Bank transfer"),
    PAYPAL("PayPal");

    public final String label;

    private PaymentMethod(String label) {
        this.label = label;
    }
}

