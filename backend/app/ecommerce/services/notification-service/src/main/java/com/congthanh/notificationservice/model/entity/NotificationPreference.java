package com.congthanh.notificationservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "notification_preferences")
public class NotificationPreference {

    @Id
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "sms_enabled")
    private Boolean smsEnabled;

    @Column(name = "email_enabled")
    private Boolean emailEnabled;

    @Column(name = "in_app_enabled")
    private Boolean inAppEnabled;

    @Column(name = "web_push_enabled")
    private Boolean webPushEnabled;

    @Column(name = "do_not_disturb_start_time")
    private Instant doNotDisturbStartTime;

    @Column(name = "do_not_disturb_end_time")
    private Instant doNotDisturbEndTime;
}
