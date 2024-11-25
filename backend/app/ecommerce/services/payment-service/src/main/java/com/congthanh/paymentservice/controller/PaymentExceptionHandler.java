package com.congthanh.paymentservice.controller;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PaymentExceptionHandler {

//    @ExceptionHandler(PaymentValidationException.class)
//    public ResponseEntity<ErrorResponse> handleValidationException(
//            PaymentValidationException ex
//    ) {
//        return ResponseEntity
//                .badRequest()
//                .body(new ErrorResponse("VALIDATION_ERROR", ex.getMessage()));
//    }
//
//    @ExceptionHandler(PaymentProcessingException.class)
//    public ResponseEntity<ErrorResponse> handleProcessingException(
//            PaymentProcessingException ex
//    ) {
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(new ErrorResponse("PROCESSING_ERROR", ex.getMessage()));
//    }
//
//    @ExceptionHandler(DuplicatePaymentException.class)
//    public ResponseEntity<ErrorResponse> handleDuplicatePayment(
//            DuplicatePaymentException ex
//    ) {
//        return ResponseEntity
//                .status(HttpStatus.CONFLICT)
//                .body(new ErrorResponse("DUPLICATE_PAYMENT", ex.getMessage()));
//    }
}
