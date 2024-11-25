package com.congthanh.notificationservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {

  private Long id;

  @NotNull
  private String customer;

  private String title;

  private String content;

  private String url;

  @JsonProperty("isRead")
  private boolean isRead;

  private long createdAt;

  private String status;

}
