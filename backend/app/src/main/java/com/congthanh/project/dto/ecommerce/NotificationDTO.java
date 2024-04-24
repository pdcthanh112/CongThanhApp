package com.congthanh.project.dto.ecommerce;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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

  private long createdDate;

}
