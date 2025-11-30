package com.congthanh.notificationservice.service.factory;

import com.congthanh.notificationservice.constant.enums.NotificationChannel;
import com.congthanh.notificationservice.service.strategy.NotificationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class NotificationStrategyFactory implements NotificationProviderFactory {

    private final Map<NotificationChannel, NotificationStrategy> strategies;

    @Autowired
    public NotificationStrategyFactory(List<NotificationStrategy> strategyList) {
        this.strategies = strategyList.stream().collect(Collectors.toMap(NotificationStrategy::getMethod, strategy -> strategy));
    }

    @Override
    public NotificationStrategy createNotificationStrategy(NotificationChannel type) {
        return Optional.ofNullable(strategies.get(type))
                .orElseThrow(() -> new IllegalArgumentException("Invalid notification method"));
    }
}
