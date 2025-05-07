package com.congthanh.notificationservice.controller;

import com.congthanh.notificationservice.constant.common.ResponseStatus;
import com.congthanh.notificationservice.model.dto.NotificationDTO;
import com.congthanh.notificationservice.model.request.FCMSubscriptionRequest;
import com.congthanh.notificationservice.model.request.WebPushSubscriptionRequest;
import com.congthanh.notificationservice.model.response.Response;
import com.congthanh.notificationservice.service.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/notifications")
@Tag(name = "Notification API", description = "Notification API in CongThanhApp - Ecommerce")
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationService notificationService;

  @GetMapping("/customer/{customerId}")
  public ResponseEntity<Response<List<NotificationDTO>>> getNotificationByCustomer(@PathVariable("customerId") String customerId) {
    List<NotificationDTO> result = notificationService.getNotificationByCustomer(customerId);
    Response<List<NotificationDTO>> response = new Response<>();
    response.setData(result);
    response.setStatus(ResponseStatus.STATUS_SUCCESS);
    response.setMessage(result != null ? "Get xong" : "Noti emply");
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("")
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

  @PostMapping("/subscribe")
  public ResponseEntity saveFCMToken(@RequestBody FCMSubscriptionRequest request) {
    notificationService.saveFCMSubscription(request.userId(), request.deviceToken());
    return ResponseEntity.ok().build();
  }

  @PostMapping("/subscribe-webpush")
  public ResponseEntity saveWebPushSubscription(@RequestBody WebPushSubscriptionRequest request) {
    notificationService.saveWebPushSubscription(request.userId(), request.subscription());
    return ResponseEntity.ok().build();
  }
}
