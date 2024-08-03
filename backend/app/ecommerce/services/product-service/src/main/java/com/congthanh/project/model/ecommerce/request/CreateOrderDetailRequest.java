package com.congthanh.project.model.ecommerce.request;

import com.congthanh.project.entity.Order;
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

    private Order order;

}
