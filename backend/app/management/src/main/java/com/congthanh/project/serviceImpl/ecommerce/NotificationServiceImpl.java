package com.congthanh.project.serviceImpl.ecommerce;

import com.congthanh.project.dto.ecommerce.NotificationDTO;
import com.congthanh.project.entity.ecommerce.Notification;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.model.ecommerce.mapper.NotificationMapper;
import com.congthanh.project.repository.ecommerce.notification.NotificationRepository;
import com.congthanh.project.service.ecommerce.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;

  @Override
  public List<NotificationDTO> getNotificationByCustomer(String customerId) {
    List<Notification> data = notificationRepository.getNotificationByCustomer(customerId);
    if (data != null || data.size() > 0) {
      List<NotificationDTO> result = new ArrayList<>();
      for (Notification item : data) {
        NotificationDTO notificationDTO = NotificationMapper.mapNotificationEntityToDTO(item);
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
            .url(notificationDTO.getUrl())
            .isRead(false)
            .status("ACTIVE")
            .build();
    Notification result = notificationRepository.save(notification);
    return NotificationMapper.mapNotificationEntityToDTO(result);
  }

  @Override
  public boolean changeNotificationReadStatus(Long notificationId, boolean status) {
    Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new NotFoundException("Notification not found"));
    boolean result = notificationRepository.changeNotificationReadStatus(notification.getId(), status);
    if(result) {
      return result;
    } else {
      throw new RuntimeException("Error");
    }
  }
}
