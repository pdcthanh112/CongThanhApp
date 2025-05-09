package com.congthanh.cartservice.model.dto;

import com.congthanh.cartservice.constant.enums.CartStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String customer;

    private Instant createdAt;

    private Instant updatedAt;

    private CartStatus status;

    private Set<CartItemDTO> cartItems;

    @JsonProperty("isDefault")
    private boolean isDefault;

//    private CheckoutDTO checkout;

//    public BigDecimal getTotalOrderPrice() {
//        BigDecimal sum = BigDecimal.ZERO;
//        if (this.cartItems == null || this.cartItems.isEmpty()) {
//            return BigDecimal.ZERO;
//        }
//        Set<CartItemDTO> orderProducts = this.cartItems;
//        for (CartItemDTO item : orderProducts) {
//            BigDecimal quantityDecimal = BigDecimal.valueOf(item.getQuantity());
//            BigDecimal itemTotal = quantityDecimal.multiply(item.getProduct().getPrice());
//            sum = sum.add(itemTotal);
//        }
//        return sum;
//    }

    public int getCountItem() {
        if (this.cartItems == null || this.cartItems.isEmpty()) {
            return 0;
        }
        return this.cartItems.size();
    }

}
