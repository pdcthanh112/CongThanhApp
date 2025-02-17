package com.congthanh.cartservice.controller;

import com.congthanh.cartservice.constant.common.ResponseStatus;
import com.congthanh.cartservice.model.dto.CartDTO;
import com.congthanh.cartservice.model.dto.CartItemDTO;
import com.congthanh.cartservice.model.request.CreateCartRequest;
import com.congthanh.cartservice.model.response.Response;
import com.congthanh.cartservice.model.entity.Cart;
import com.congthanh.cartservice.repository.cart.CartRepository;
import com.congthanh.cartservice.service.CartItemService;
import com.congthanh.cartservice.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/carts")
@Tag(name = "Cart API", description = "Cart API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
@Slf4j
public class CartController {

  private final CartRepository cartRepository;

  private final CartService cartService;

  private final CartItemService cartItemService;

  @GetMapping("/{id}")
  public ResponseEntity<Response<CartDTO>> getCartById(@PathVariable("id") @NotBlank(message = "Input must not be blank") @Valid final Long id) {
    CartDTO result = cartService.getCartById(id);
    Response<CartDTO> response = new Response<>();
    response.setData(result);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage("Get xong");
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/customer/{customerId}")
  public ResponseEntity<Response<List<CartDTO>>> getAllCartByCustomerId(@PathVariable("customerId") String customerId) {
    List<CartDTO> result = cartService.getActiveCartByCustomerId(customerId);
    Response<List<CartDTO>> response = new Response<>();
    response.setData(result);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage(result != null ? "Get xong" : "Cart emply");
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/{cartId}/items")
  public ResponseEntity<Response<List<CartDTO>>> getAllItemByCartId(@PathVariable("cartId") String cartId) {
    List<CartDTO> result = cartService.getActiveCartByCustomerId(cartId);
    Response<List<CartDTO>> response = new Response<>();
    response.setData(result);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage(result != null ? "Get xong" : "Cart emply");
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/")
  public ResponseEntity<Response<CartDTO>> createCart(@RequestBody CreateCartRequest request) {
    CartDTO data = cartService.createCart(request);
    Response<CartDTO> response = new Response<>();
    response.setData(data);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage("Created successfully");
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Response<String>> deleteCart(@PathVariable Long id) {
    Response<String> response = new Response<>();
    cartService.deleteCart((id));
    response.setData(null);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage("Delete successfully");
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/{cartId}/items")
  public ResponseEntity<Response<CartItemDTO>> addItemToCart(@RequestParam String productId, @RequestParam int quantity, @PathVariable("cartId") Long cartId) {
    CartItemDTO result = cartItemService.addToCart(productId, quantity, cartId);
    Response<CartItemDTO> response = new Response<>();
    response.setData(result);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage("Add to cart successfully");
    return ResponseEntity.ok().body(response);
  }

  @PutMapping("/{cartId}/items/{itemId}")
  public ResponseEntity<Response<CartItemDTO>> updateCartItem(@PathVariable("itemId") Long cartItemId, @RequestParam int quantity) {
    CartItemDTO cartItem = cartItemService.updateCartItem(cartItemId, quantity);
    Response<CartItemDTO> response = new Response<>();
    response.setData(cartItem);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage("Update successfully");
    return ResponseEntity.ok().body(response);
  }

  @DeleteMapping("/{cartId}/items/{itemId}")
  public ResponseEntity<Response<String>> deleteCartItem(@PathVariable("itemId") Long id) {
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

  @QueryMapping(value = "cart")
  List<Cart> carts() {
    return cartRepository.findAll();
  }
}
