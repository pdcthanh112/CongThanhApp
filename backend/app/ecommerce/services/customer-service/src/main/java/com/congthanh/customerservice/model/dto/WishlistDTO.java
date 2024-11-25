package com.congthanh.customerservice.model.dto;

import com.congthanh.customerservice.model.response.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistDTO {

    private Long id;

    private String customer;

    private Set<ProductResponse> product;

}