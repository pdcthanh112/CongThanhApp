package com.congthanh.project.constant.enums;

public enum OrderStatus {
    ALL,
    PENDING, // Đơn hàng đã được tạo nhưng chưa được thanh toán hoặc xác nhận.
    PROCESSING, // Đơn hàng đang trong quá trình xử lý, bao gồm cả xác nhận thanh toán và chuẩn bị giao hàng.
    SHIPPED, // Đơn hàng đã được giao cho đơn vận chuyển và đang trong quá trình vận chuyển đến địa chỉ của khách hàng.
    DELIVERED, // Đơn hàng đã được giao thành công và đã đến địa chỉ được chỉ định.
    CANCELED, // Đơn hàng đã bị hủy bỏ, thường là do yêu cầu của khách hàng hoặc do vấn đề nào đó.
    REFUNDED, // Khách hàng đã nhận được hoàn trả tiền cho đơn hàng.
    RETURNED, // Khách hàng đã trả lại sản phẩm, có thể đang chờ xử lý hoàn trả.
    COMPLETED, // Đơn hàng đã hoàn tất toàn bộ quá trình, từ xác nhận thanh toán đến giao hàng thành công.
}

