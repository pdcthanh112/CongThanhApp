package com.congthanh.notificationservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderDetailRequest {

    private String productId;

    private int quantity;

    private String order;

}
