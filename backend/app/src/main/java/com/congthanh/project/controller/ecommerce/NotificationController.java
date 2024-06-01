package com.congthanh.project.controller.ecommerce;

import com.congthanh.project.constant.common.ResponseStatus;
import com.congthanh.project.dto.ecommerce.NotificationDTO;
import com.congthanh.project.model.ecommerce.response.Response;
import com.congthanh.project.service.ecommerce.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/notification")
@Tag(name = "Notification API", description = "Notification API in CongThanhApp - Ecommerce")
public class NotificationController {

  @Autowired
  private NotificationService notificationService;

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
}
