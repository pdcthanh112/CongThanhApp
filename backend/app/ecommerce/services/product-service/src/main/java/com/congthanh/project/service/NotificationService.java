package com.congthanh.project.service.ecommerce;

import com.congthanh.project.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {

  List<NotificationDTO> getNotificationByCustomer(String customerId);

  NotificationDTO createNotification(NotificationDTO notificationDTO);

  boolean changeNotificationReadStatus(Long notificationId, boolean status);

}
