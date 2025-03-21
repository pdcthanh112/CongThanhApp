package com.congthanh.customerservice.controller;

import com.congthanh.customerservice.constant.common.ResponseStatus;
import com.congthanh.customerservice.model.dto.WishlistDTO;
import com.congthanh.customerservice.model.response.Response;
import com.congthanh.customerservice.service.WishlistService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ecommerce/customer")
@Tag(name = "Wishlist API", description = "Wishlist API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class WishlistController {

  private final WishlistService wishlistService;

  @GetMapping("/{customerId}/wishlist")
  public ResponseEntity<Response<WishlistDTO>> getWishlistByCustomer(@PathVariable("customerId") String customerId) {
    WishlistDTO data = wishlistService.getWishlistByCustomer(customerId);
    Response<WishlistDTO> response = new Response<>();
    response.setData(data);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage("Get successfully");
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/wishlist")
  public ResponseEntity<Response<?>> addProductToWishlist(@RequestBody Map<String, String> requestData) {
    String customerId = requestData.get("customerId");
    String productId = requestData.get("productId");
    Response<String> response = new Response<>();
    try {
      wishlistService.addProductToWishlist(customerId, productId);
      response.setData(null);
      response.setStatus(ResponseStatus.STATUS_SUCCESS);
      response.setMessage("Add successfully");
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      response.setData(null);
      response.setStatus(ResponseStatus.STATUS_FAILED);
      response.setMessage("Product already exists in wishlist");
      response.setErrorCode(403101);
      return ResponseEntity.status(403).body(response);
    }
  }

  @DeleteMapping("/wishlist")
  public ResponseEntity<Response<String>> removeProductFromWishlist(@RequestBody Map<String, String> requestData) {
    String customerId = requestData.get("customerId");
    String productId = requestData.get("productId");
    boolean result = wishlistService.removeProductFromWishlist(customerId, productId);
    if (result) {
      Response<String> response = new Response();
      response.setData(null);
      response.setStatus(ResponseStatus.STATUS_SUCCESS);
      response.setMessage("Remove successfully");
      return ResponseEntity.ok().body(response);
    } else {
      throw new RuntimeException("Product khong ton tai");
    }
  }
}