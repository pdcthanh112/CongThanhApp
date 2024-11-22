package com.congthanh.project.controller;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.model.dto.NotificationDTO;
import com.congthanh.project.model.response.Response;
import com.congthanh.project.service.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/notification")
@Tag(name = "Notification API", description = "Notification API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationService notificationService;

  @GetMapping("/getByCustomer")
  public ResponseEntity<Response<List<NotificationDTO>>> getNotificationByCustomer(@RequestParam("id") String customerId) {
    List<NotificationDTO> result = notificationService.getNotificationByCustomer(customerId);
    Response<List<NotificationDTO>> response = new Response<>();
    response.setData(result);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage(result != null ? "Get xong" : "Noti emply");
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/create")
  public ResponseEntity<Response<NotificationDTO>> createNotification(@RequestBody NotificationDTO notificationDTO) {
    NotificationDTO result = notificationService.createNotification(notificationDTO);
    Response<NotificationDTO> response = new Response<>();
    response.setData(result);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage("Created successfully");
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PatchMapping("/change-read-status/{id}")
  public ResponseEntity<Response<NotificationDTO>> changeReadStatus(@PathVariable("id") Long notificationId, @RequestParam boolean status) {
    notificationService.changeNotificationReadStatus(notificationId, status);
    Response<NotificationDTO> response = new Response<>();
    response.setData(null);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage("Change reading successfully");
    return ResponseEntity.ok().body(response);
  }
}
