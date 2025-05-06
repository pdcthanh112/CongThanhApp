package com.congthanh.notificationservice.service.serviceImpl;

import com.congthanh.notificationservice.constant.enums.NotificationStatus;
import com.congthanh.notificationservice.model.dto.NotificationDTO;
import com.congthanh.notificationservice.model.entity.Notification;
import com.congthanh.notificationservice.exception.NotFoundException;
import com.congthanh.notificationservice.model.mapper.NotificationMapper;
import com.congthanh.notificationservice.repository.NotificationRepository;
import com.congthanh.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
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
            .status(NotificationStatus.CREATED)
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

  @KafkaListener(topics = "notification")
  public void processNotification(NotificationEvent event) {
    try {
      log.info("Processing notification of type {} for recipient {}", event.getType(), event.getRecipientId());

      // Lưu thông báo
      Notification notification = new Notification();
      notification.setRecipientId(event.getRecipientId());
      notification.setType(event.getType());
      notification.setMessage(event.getMessage());
      notification.setTimestamp(new Date());
      notification.setStatus(NotificationStatus.PENDING);

      notification = notificationRepository.save(notification);

      // Lấy thông tin khách hàng
      Customer customer = customerRepository.findById(event.getRecipientId())
              .orElse(null);

      if (customer == null) {
        log.warn("Customer not found for ID: {}", event.getRecipientId());
        notification.setStatus(NotificationStatus.FAILED);
        notification.setFailureReason("Customer not found");
        notificationRepository.save(notification);
        return;
      }

      // Gửi thông báo theo preference của khách hàng
      boolean notificationSent = false;

      if (customer.isEmailNotificationsEnabled() && customer.getEmail() != null) {
        try {
          emailService.sendEmail(
                  customer.getEmail(),
                  getEmailSubject(event.getType()),
                  event.getMessage()
          );
          notificationSent = true;
        } catch (Exception e) {
          log.error("Failed to send email to {}: {}", customer.getEmail(), e.getMessage());
        }
      }

      if (customer.isSmsNotificationsEnabled() && customer.getPhone() != null) {
        try {
          smsService.sendSMS(
                  customer.getPhone(),
                  event.getMessage()
          );
          notificationSent = true;
        } catch (Exception e) {
          log.error("Failed to send SMS to {}: {}", customer.getPhone(), e.getMessage());
        }
      }

      // Cập nhật trạng thái thông báo
      if (notificationSent) {
        notification.setStatus(NotificationStatus.SENT);
        log.info("Notification sent successfully to customer {}", event.getRecipientId());
      } else {
        notification.setStatus(NotificationStatus.FAILED);
        notification.setFailureReason("No notification channels available or all channels failed");
        log.warn("Failed to send notification to customer {}: no available channels", event.getRecipientId());
      }

      notificationRepository.save(notification);

    } catch (Exception e) {
      log.error("Error processing notification for recipient {}: {}", event.getRecipientId(), e.getMessage());
    }
  }
}
