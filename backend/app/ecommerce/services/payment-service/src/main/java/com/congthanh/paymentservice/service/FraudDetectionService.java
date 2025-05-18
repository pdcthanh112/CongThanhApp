//package com.congthanh.paymentservice.service;
//
//import com.congthanh.paymentservice.model.request.PaymentRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.util.Set;
//import java.util.stream.Stream;
//
//@Component
//@RequiredArgsConstructor
//public class FraudDetectionService {
//
//    private final RuleEngine ruleEngine;
//
//    public FraudCheckResult assessFraudRisk(PaymentRequest request) {
//        return FraudCheckResult.builder()
//                .checkCreditCardBin(request)
//                .checkTransactionVelocity(request)
//                .checkDeviceFingerprint(request)
//                .build();
//    }
//
//    @Component
//    public class RuleEngine {
//        public boolean applyRules(PaymentRequest request) {
//            return Stream.of(
//                    checkAmountThreshold(request),
//                    checkHighRiskCountries(request),
//                    checkSuspiciousDevicePatterns(request)
//            ).allMatch(Boolean::booleanValue);
//        }
//
//        private boolean checkAmountThreshold(PaymentRequest request) {
//            return request.getAmount().compareTo(BigDecimal.valueOf(10000)) < 0;
//        }
//
//        private boolean checkHighRiskCountries(PaymentRequest request) {
//            Set<String> highRiskCountries = Set.of("RU", "NG", "VE");
//            return !highRiskCountries.contains(request.getAdditionalInfo().get("country"));
//        }
//
//        private boolean checkSuspiciousDevicePatterns(PaymentRequest request) {
//            // Logic check device fingerprint
//            return true;
//        }
//    }
//}
