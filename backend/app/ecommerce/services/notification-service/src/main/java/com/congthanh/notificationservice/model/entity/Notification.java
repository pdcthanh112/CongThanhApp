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
@Table(name = "notifications")
public class Notification {

  @Id
  private Long id;

  private String title;

  private String content;

  private String url;

  @Enumerated(EnumType.STRING)
  private NotificationType type;

  @CreationTimestamp
  @Column(name = "created_at")
  private Instant createdAt;

}
