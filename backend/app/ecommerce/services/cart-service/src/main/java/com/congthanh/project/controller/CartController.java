package com.congthanh.project.controller;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.model.dto.CartDTO;
import com.congthanh.project.model.response.Response;
import com.congthanh.project.model.entity.Cart;
import com.congthanh.project.repository.cart.CartRepository;
import com.congthanh.project.service.CartService;
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
@RequestMapping("/ecommerce/cart")
@Tag(name = "Cart API", description = "Cart API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
@Slf4j
public class CartController {

  private final CartRepository cartRepository;

  private final CartService cartService;

  @GetMapping("/{id}")
  public ResponseEntity<Response<CartDTO>> getCartById(@PathVariable("id") @NotBlank(message = "Input must not be blank") @Valid final String id) {
    log.info("Get cart by id");
    CartDTO result = cartService.getCartById(id);
    Response<CartDTO> response = new Response<>();
    response.setData(result);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage("Get xong");
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/getByCustomer")
  public ResponseEntity<Response<List<CartDTO>>> getAllCartByCustomerId(@RequestParam String customerId) {
    List<CartDTO> result = cartService.getActiveCartByCustomerId(customerId);
    Response<List<CartDTO>> response = new Response<>();
    response.setData(result);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage(result != null ? "Get xong" : "Cart emply");
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/get-item")
  public ResponseEntity<Response<List<CartDTO>>> getAllItemByCartId(@RequestParam("cart") String cartId) {
    List<CartDTO> result = cartService.getActiveCartByCustomerId(cartId);
    Response<List<CartDTO>> response = new Response<>();
    response.setData(result);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage(result != null ? "Get xong" : "Cart emply");
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/create")
  public ResponseEntity<Response<CartDTO>> createCart(@RequestBody CartDTO cartDTO) {
    CartDTO data = cartService.createCart(cartDTO);
    Response<CartDTO> response = new Response<>();
    response.setData(data);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage("Created successfully");
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Response<String>> deleteCart(@PathVariable String id) {
    Response<String> response = new Response<>();
    cartService.deleteCart((id));
    response.setData(null);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage("Delete successfully");
    return ResponseEntity.ok().body(response);
  }

  @QueryMapping(value = "cart")
  List<Cart> carts() {
    return cartRepository.findAll();
  }
}
