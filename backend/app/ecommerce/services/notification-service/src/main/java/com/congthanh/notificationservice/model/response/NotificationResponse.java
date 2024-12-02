package com.congthanh.notificationservice.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationResponse {
    private boolean success;
    private String messageId;
    private String errorMessage;
}
