package com.congthanh.paymentservice.exception.ecommerce;

public class UnsupportedPaymentMethodException extends RuntimeException {
    public UnsupportedPaymentMethodException() {
        super();
    }

    public UnsupportedPaymentMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedPaymentMethodException(String message) {
        super(message);
    }

    public UnsupportedPaymentMethodException(Throwable cause) {
        super(cause);
    }

}
