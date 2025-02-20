package com.congthanh.notificationservice.model.entity;

import com.congthanh.notificationservice.constant.enums.NotificationStatus;
import com.congthanh.notificationservice.constant.enums.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notification")
public class Notification {

  @Id
  private Long id;

  private String customer;

  private String title;

  private String content;

  private String url;

  @Column(name = "sent_at")
  private Instant sentAt;

  @Column(name = "read_at")
  private Instant readAt;

  @Enumerated(EnumType.STRING)
  private NotificationType type;

  private String image;

  @CreationTimestamp
  @Column(name = "created_at")
  private Instant createdAt;

  @Enumerated(EnumType.STRING)
  private NotificationStatus status;

}
