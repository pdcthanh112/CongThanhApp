package com.congthanh.paymentservice.exception.ecommerce;

public class PaymentGatewayTemporaryErrorException extends RuntimeException {

  public PaymentGatewayTemporaryErrorException() {
    super();
  }

  public PaymentGatewayTemporaryErrorException(String message, Throwable cause) {
    super(message, cause);
  }

  public PaymentGatewayTemporaryErrorException(String message) {
    super(message);
  }

  public PaymentGatewayTemporaryErrorException(Throwable cause) {
    super(cause);
  }

}
