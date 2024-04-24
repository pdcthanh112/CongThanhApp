package com.congthanh.project.service.ecommerce;

import com.congthanh.project.dto.ecommerce.NotificationDTO;
import com.congthanh.project.entity.ecommerce.Notification;

import java.util.List;

public interface NotificationService {

  List<NotificationDTO> getNotificationByCustomer(String customerId);

  NotificationDTO createNotification(NotificationDTO notificationDTO);

}
