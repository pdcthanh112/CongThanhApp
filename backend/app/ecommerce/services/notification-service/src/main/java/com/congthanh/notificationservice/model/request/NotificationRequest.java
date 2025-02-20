package com.congthanh.notificationservice.model.request;

import com.congthanh.notificationservice.constant.enums.NotificationMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class NotificationRequest {

    @NotNull
    private NotificationMethod type;

    @NotBlank
    private String recipient;

    private String subject;
    private String content;

    // FCM Specific
    private String deviceToken;
    private Map<String, String> data;

    // SMS Specific
    private String phoneNumber;

    // Email Specific
    private String[] ccRecipients;
    private String[] bccRecipients;
    private List<File> attachments;

    private Map<String, String> additionalInfo;

}
