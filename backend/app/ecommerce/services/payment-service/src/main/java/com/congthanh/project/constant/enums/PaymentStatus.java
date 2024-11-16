package com.congthanh.project.constant.enums;

public enum PaymentStatus {
    SUCCESS,              // Thanh toán thành công
    PENDING,             // Đang chờ xử lý
    FAILED,              // Thanh toán thất bại
    CANCELLED,           // Bị hủy
    REFUNDED,            // Đã hoàn tiền
    PARTIALLY_REFUNDED,  // Hoàn tiền một phần
    EXPIRED,             // Hết hạn
    PROCESSING,          // Đang xử lý
    AUTHORIZED,          // Đã xác thực nhưng chưa capture
    CAPTURED,            // Đã capture
    CHARGEBACK,          // Đã chargeback
    DISPUTED             // Đang tranh chấp
}
