package com.congthanh.cartservice.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCartRequest {

    private String name;

    private String customerId;

    private boolean isDefault;

}
