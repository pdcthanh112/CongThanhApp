package com.congthanh.project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private int quantity;

    @NotNull
    private String product;

    private String order;

    private String status;

//    public BigDecimal getTotal() {
//        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
//    }

}
