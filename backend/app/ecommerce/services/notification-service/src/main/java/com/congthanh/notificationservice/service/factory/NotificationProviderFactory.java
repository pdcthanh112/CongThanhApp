package com.congthanh.notificationservice.service.factory;

import com.congthanh.notificationservice.constant.enums.NotificationMethod;
import com.congthanh.notificationservice.service.strategy.NotificationStrategy;

public interface NotificationProviderFactory {

    NotificationStrategy createNotificationStrategy(NotificationMethod type);
}
