package com.congthanh.notificationservice.service.factory;

import com.congthanh.notificationservice.constant.enums.NotificationChannel;
import com.congthanh.notificationservice.service.strategy.NotificationStrategy;

public interface NotificationProviderFactory {

    NotificationStrategy createNotificationStrategy(NotificationChannel type);
}
