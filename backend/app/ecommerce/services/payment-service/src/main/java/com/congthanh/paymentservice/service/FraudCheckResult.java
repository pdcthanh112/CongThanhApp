package com.congthanh.paymentservice.service;

import com.congthanh.paymentservice.model.request.PaymentRequest;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class FraudCheckResult {
    private boolean suspicious;
    private FraudRiskLevel riskLevel;
    private List<FraudReason> fraudReasons;
    private BigDecimal riskScore;

    // Enum mức độ rủi ro
    public enum FraudRiskLevel {
        LOW_RISK,
        MEDIUM_RISK,
        HIGH_RISK,
        CRITICAL_RISK
    }

    // Enum các lý do gian lận
    public enum FraudReason {
        SUSPICIOUS_DEVICE,
        UNUSUAL_TRANSACTION_AMOUNT,
        HIGH_RISK_LOCATION,
        VELOCITY_THRESHOLD_EXCEEDED,
        ABNORMAL_PURCHASE_PATTERN,
        MISMATCHED_BILLING_DETAILS
    }

    // Builder method mở rộng
    public static class FraudCheckResultBuilder {
        public FraudCheckResultBuilder checkCreditCardBin(PaymentRequest request) {
//            CreditCardBinInfo binInfo = getCreditCardBinInfo(request.getCardBin());
            CreditCardBinInfo binInfo = getCreditCardBinInfo(request.getAdditionalInfo().get("card_bin"));

            if (binInfo.isHighRiskCountry()) {
                fraudReasons.add(FraudReason.HIGH_RISK_LOCATION);
                riskScore = riskScore.add(BigDecimal.valueOf(20));
            }

            return this;
        }

        public FraudCheckResultBuilder checkTransactionVelocity(PaymentRequest request) {
            // Kiểm tra tần suất giao dịch
//            int transactionsInLastHour = getTransactionsCount(request.getUserId());
            int transactionsInLastHour = getTransactionsCount(request.getAdditionalInfo().get("user_id"));

            if (transactionsInLastHour > 5) {
                fraudReasons.add(FraudReason.VELOCITY_THRESHOLD_EXCEEDED);
                riskScore = riskScore.add(BigDecimal.valueOf(30));
            }

            return this;
        }

        public FraudCheckResultBuilder checkDeviceFingerprint(PaymentRequest request) {
            // Kiểm tra dấu vân tay thiết bị
            DeviceFingerprint fingerprint = generateDeviceFingerprint(request);

            if (isUnknownDevice(fingerprint)) {
                fraudReasons.add(FraudReason.SUSPICIOUS_DEVICE);
                riskScore = riskScore.add(BigDecimal.valueOf(15));
            }

            return this;
        }

        public FraudCheckResult build() {
            // Tính toán mức độ rủi ro dựa trên điểm số
            FraudRiskLevel riskLevel = calculateRiskLevel(riskScore);
            suspicious = riskLevel != FraudRiskLevel.LOW_RISK;

            return new FraudCheckResult(
                    suspicious,
                    riskLevel,
                    fraudReasons,
                    riskScore
            );
        }

        private FraudRiskLevel calculateRiskLevel(BigDecimal riskScore) {
            if (riskScore.compareTo(BigDecimal.valueOf(60)) >= 0) {
                return FraudRiskLevel.CRITICAL_RISK;
            } else if (riskScore.compareTo(BigDecimal.valueOf(40)) >= 0) {
                return FraudRiskLevel.HIGH_RISK;
            } else if (riskScore.compareTo(BigDecimal.valueOf(20)) >= 0) {
                return FraudRiskLevel.MEDIUM_RISK;
            }
            return FraudRiskLevel.LOW_RISK;
        }
    }

    // Các helper method để hỗ trợ việc kiểm tra
    private static class CreditCardBinInfo {
        private String bin;
        private String country;

        public boolean isHighRiskCountry() {
            Set<String> highRiskCountries = Set.of("RU", "NG", "VE");
            return highRiskCountries.contains(country);
        }
    }

    private static class UserLocation {
        private double latitude;
        private double longitude;
    }

    private static class DeviceFingerprint {
        private String deviceId;
        private String browserSignature;
        private String ipAddress;
    }

    // Các phương thức hỗ trợ (giả định)
    private static CreditCardBinInfo getCreditCardBinInfo(String bin) {
        // Lookup BIN info từ database hoặc external service
        return new CreditCardBinInfo();
    }

    private static int getTransactionsCount(String userId) {
        // Đếm số lượng giao dịch của user trong 1 giờ qua
        return 0;
    }

    private static DeviceFingerprint generateDeviceFingerprint(PaymentRequest request) {
        // Tạo dấu vân tay thiết bị
        return new DeviceFingerprint();
    }

    private static boolean isUnknownDevice(DeviceFingerprint fingerprint) {
        // Kiểm tra xem thiết bị có phải là thiết bị mới/lạ không
        return false;
    }
}
