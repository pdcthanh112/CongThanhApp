package com.congthanh.orderservice.controller;

import com.congthanh.orderservice.constant.common.ResponseStatus;
import com.congthanh.orderservice.model.dto.OrderDTO;
import com.congthanh.orderservice.model.request.CreateOrderRequest;
import com.congthanh.orderservice.model.response.Response;
import com.congthanh.orderservice.model.viewmodel.OrderDetailVm;
import com.congthanh.orderservice.model.viewmodel.OrderHistoryVm;
import com.congthanh.orderservice.service.OrderItemService;
import com.congthanh.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/orders")
@Tag(name = "Order API", description = "Order API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  private final OrderItemService orderItemService;

  @PostMapping("")
  public ResponseEntity<Response<OrderDTO>> createOrder(@RequestParam CreateOrderRequest request) {
    OrderDTO data = orderService.createOrder(request);
    Response<OrderDTO> response = new Response<>();
    response.setData(data);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage("Get all successfully");
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/detail/{orderCode}")
  public ResponseEntity<Response<OrderDetailVm>> getOrderDetail(@PathVariable("orderCode") String orderCode) {
    OrderDetailVm data = orderService.getOrderDetailByCode(orderCode);
    Response<OrderDetailVm> response = new Response<>();
    response.setData(data);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage("Get successfully");
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/history")
  public ResponseEntity<Response<List<OrderHistoryVm>>> getOrderHistory(@RequestParam("customerId") String customerId) {
    List<OrderHistoryVm> data = orderService.getOrderHistoryByCustomer(customerId);
    Response<List<OrderHistoryVm>> response = new Response<>();
    response.setData(data);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage("Get successfully");
    return ResponseEntity.ok().body(response);
  }


}
