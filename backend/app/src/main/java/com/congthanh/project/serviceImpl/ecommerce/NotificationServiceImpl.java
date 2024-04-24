package com.congthanh.project.serviceImpl.ecommerce;

import com.congthanh.project.dto.ecommerce.NotificationDTO;
import com.congthanh.project.entity.ecommerce.Notification;
import com.congthanh.project.model.ecommerce.mapper.NotificationMapper;
import com.congthanh.project.repository.ecommerce.notification.NotificationRepository;
import com.congthanh.project.service.ecommerce.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

  @Autowired
  private NotificationRepository notificationRepository;

  @Autowired
  private NotificationMapper notificationMapper;

  @Override
  public List<NotificationDTO> getNotificationByCustomer(String customerId) {
    List<Notification> data = notificationRepository.getNotificationByCustomer(customerId);
    if (data != null || data.size() > 0) {
      List<NotificationDTO> result = new ArrayList<>();
      for (Notification item : data) {
        NotificationDTO notificationDTO = notificationMapper.mapNotificationEntityToDTO(item);
        result.add(notificationDTO);
      }
      return result;
    }
    return null;
  }

  @Override
  public NotificationDTO createNotification(NotificationDTO notificationDTO) {
    Notification notification = Notification.builder()
            .customer(notificationDTO.getCustomer())
            .content(notificationDTO.getContent())
            .title(notificationDTO.getTitle())
            .build();
    Notification result = notificationRepository.save(notification);
    return notificationMapper.mapNotificationEntityToDTO(result);
  }
}
