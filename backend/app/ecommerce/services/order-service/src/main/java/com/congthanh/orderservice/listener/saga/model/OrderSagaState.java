package com.congthanh.orderservice.listener.saga.model;

public enum OrderSagaState {
    ORDER_CREATED,        // Đơn hàng đã được tạo
    PAYMENT_COMPLETED,    // Thanh toán thành công
    PAYMENT_FAILED,       // Thanh toán thất bại
    INVENTORY_UPDATED,    // Cập nhật tồn kho thành công
    INVENTORY_FAILED,     // Cập nhật tồn kho thất bại
    DELIVERY_CREATED,     // Đã tạo đơn giao hàng
    DELIVERY_FAILED,      // Tạo đơn giao hàng thất bại
    ORDER_COMPLETED,      // Đơn hàng hoàn thành
    ORDER_CANCELLED       // Đơn hàng bị hủy
}
