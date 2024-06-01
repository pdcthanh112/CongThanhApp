package com.congthanh.project.controller.ecommerce;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.ecommerce.CartItemDTO;
import com.congthanh.project.model.ecommerce.response.Response;
import com.congthanh.project.service.ecommerce.CartItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ecommerce/cart-item")
@Tag(name = "Cart Item API", description = "Cart item API in CongThanhApp - Ecommerce")
public class CartItemController {

  @Autowired
  private CartItemService cartItemService;

  @PostMapping("/addToCart")
  public ResponseEntity<Response<CartItemDTO>> addItemToCart(@RequestParam String productId, @RequestParam int quantity, @RequestParam String cartId) {
    CartItemDTO result = cartItemService.addToCart(productId, quantity, cartId);
    Response<CartItemDTO> response = new Response<>();
    response.setData(result);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage("Add to cart successfully");
    return ResponseEntity.ok().body(response);
  }

  @PutMapping("/update")
  public ResponseEntity<Response<CartItemDTO>> updateCartItem(@RequestParam String cartItemId, @RequestParam int quantity) {
    CartItemDTO cartItem = cartItemService.updateCartItem(cartItemId, quantity);
    Response<CartItemDTO> response = new Response<>();
    response.setData(cartItem);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage("Update successfully");
    return ResponseEntity.ok().body(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Response<String>> deleteCartItem(@PathVariable String id) {
    boolean result = cartItemService.deleteCartItem(id);
    if (result) {
      Response<String> response = new Response<>();
      response.setData(null);
      response.setStatus(ResponseStatus.STATUS_SUCCESS);
      response.setMessage("Delete successfully");
      return ResponseEntity.ok().body(response);
    } else {
      throw new RuntimeException("Loi");
    }
  }
}
