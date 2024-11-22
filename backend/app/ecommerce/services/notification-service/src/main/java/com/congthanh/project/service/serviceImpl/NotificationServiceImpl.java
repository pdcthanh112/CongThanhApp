package com.congthanh.project.service.serviceImpl;

import com.congthanh.project.model.dto.NotificationDTO;
import com.congthanh.project.model.entity.Notification;
import com.congthanh.project.exception.NotFoundException;
import com.congthanh.project.model.mapper.NotificationMapper;
import com.congthanh.project.repository.NotificationRepository;
import com.congthanh.project.service.NotificationService;
import lombok.RequiredArgsConstructor;
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
