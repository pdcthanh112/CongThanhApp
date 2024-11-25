package com.congthanh.paymentservice.exception.ecommerce;

public class EncryptionException extends RuntimeException {

    public EncryptionException() {
        super();
    }

    public EncryptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public EncryptionException(String message) {
        super(message);
    }

    public EncryptionException(Throwable cause) {
        super(cause);
    }

}
