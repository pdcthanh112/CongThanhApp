package com.congthanh.notificationservice.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notification")
public class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String customer;

  private String title;

  private String content;

  private String url;

  @Column(name = "is_read")
  @JsonProperty("isRead")
  private boolean isRead;

  @Column(name = "created_at")
  private long createdAt;

  private String status;

  @PrePersist
  public void prePersist() {
    this.createdAt = Instant.now().toEpochMilli();
  }

}
