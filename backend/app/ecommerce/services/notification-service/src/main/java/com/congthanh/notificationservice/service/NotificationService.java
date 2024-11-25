package com.congthanh.notificationservice.service;

import com.congthanh.notificationservice.model.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {

  List<NotificationDTO> getNotificationByCustomer(String customerId);

  NotificationDTO createNotification(NotificationDTO notificationDTO);

  boolean changeNotificationReadStatus(Long notificationId, boolean status);

}
