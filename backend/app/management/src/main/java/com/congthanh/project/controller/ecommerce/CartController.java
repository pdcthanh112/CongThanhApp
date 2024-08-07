package com.congthanh.project.controller.ecommerce;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.ecommerce.CartDTO;
import com.congthanh.project.model.ecommerce.response.Response;
import com.congthanh.project.entity.ecommerce.Cart;
import com.congthanh.project.repository.ecommerce.cart.CartRepository;
import com.congthanh.project.service.ecommerce.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/cart")
@Tag(name = "Cart API", description = "Cart API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class CartController {

  private final CartRepository cartRepository;

  private final CartService cartService;

  @GetMapping("/{id}")
  public ResponseEntity<Response<CartDTO>> getCartById(@PathVariable String id) {
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
