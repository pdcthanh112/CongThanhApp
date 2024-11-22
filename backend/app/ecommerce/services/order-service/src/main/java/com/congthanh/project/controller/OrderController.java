package com.congthanh.project.controller;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.model.dto.CheckoutDTO;
import com.congthanh.project.model.dto.OrderDetailDTO;
import com.congthanh.project.model.ecommerce.response.Response;
import com.congthanh.project.model.response.ResponseWithPagination;
import com.congthanh.project.service.OrderDetailService;
import com.congthanh.project.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/order")
@Tag(name = "Order API", description = "Order API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  private final OrderDetailService orderDetailService;

  @GetMapping("/getByStatus")
  public ResponseEntity<Response<ResponseWithPagination<OrderDetailDTO>>> getOrderByStatus(@RequestParam("status") String status, @RequestParam("page") int page, @RequestParam("limit") int limit) {
    ResponseWithPagination<OrderDetailDTO> data = orderDetailService.getOrderDetailByStatus(status, page, limit);
    Response<ResponseWithPagination<OrderDetailDTO>> response = new Response<>();
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
