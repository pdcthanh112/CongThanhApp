package com.congthanh.project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailDTO {

    private Long id;

    @NotNull
    private int quantity;

    @NotNull
    private ProductDTO product;

    private OrderDTO order;

    private String status;

//    public BigDecimal getTotal() {
//        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
//    }

}
