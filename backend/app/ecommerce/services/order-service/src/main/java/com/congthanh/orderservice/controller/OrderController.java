package com.congthanh.orderservice.controller;

import com.congthanh.orderservice.constant.common.ResponseStatus;
import com.congthanh.orderservice.model.dto.CheckoutDTO;
import com.congthanh.orderservice.model.dto.OrderItemDTO;
import com.congthanh.orderservice.model.response.Response;
import com.congthanh.orderservice.model.response.ResponseWithPagination;
import com.congthanh.orderservice.service.OrderItemService;
import com.congthanh.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/order")
@Tag(name = "Order API", description = "Order API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  private final OrderItemService orderItemService;

  @GetMapping("/getByStatus")
  public ResponseEntity<Response<ResponseWithPagination<OrderItemDTO>>> getOrderByStatus(@RequestParam("status") String status, @RequestParam("page") int page, @RequestParam("limit") int limit) {
    ResponseWithPagination<OrderItemDTO> data = orderItemService.getOrderDetailByStatus(status, page, limit);
    Response<ResponseWithPagination<OrderItemDTO>> response = new Response<>();
    response.setData(data);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage("Get all successfully");
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/history")
  public ResponseEntity<Response<List<CheckoutDTO>>> getHistoryByCustomer(@RequestParam String customerId) {
    List<CheckoutDTO> data = orderService.getHistoryByCustomer(customerId);
    Response<List<CheckoutDTO>> response = new Response<>();
    response.setData(data);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage("Get all successfully");
    return ResponseEntity.ok().body(response);
  }
}
