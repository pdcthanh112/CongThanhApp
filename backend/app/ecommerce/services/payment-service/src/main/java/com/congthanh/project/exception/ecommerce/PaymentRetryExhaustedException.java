package com.congthanh.project.exception.ecommerce;

public class PaymentRetryExhaustedException extends RuntimeException {

    public PaymentRetryExhaustedException() {
        super();
    }

    public PaymentRetryExhaustedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentRetryExhaustedException(String message) {
        super(message);
    }

    public PaymentRetryExhaustedException(Throwable cause) {
        super(cause);
    }

}
