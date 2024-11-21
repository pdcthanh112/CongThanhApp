package com.congthanh.project.exception.ecommerce;

public class PaymentValidationException extends RuntimeException {

    public PaymentValidationException() {
        super();
    }

    public PaymentValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentValidationException(String message) {
        super(message);
    }

    public PaymentValidationException(Throwable cause) {
        super(cause);
    }

}
