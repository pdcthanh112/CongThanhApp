package com.congthanh.notificationservice.model.mapper;

import com.congthanh.notificationservice.model.dto.NotificationDTO;
import com.congthanh.notificationservice.model.entity.Notification;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    private static  final  ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void configureModelMapper() {

    }

    public static Notification mapNotificationDTOToEntity(NotificationDTO notificationDTO) {
        return modelMapper.map(notificationDTO, Notification.class);
    }

    public static NotificationDTO mapNotificationEntityToDTO(Notification notification) {
        return modelMapper.map(notification, NotificationDTO.class);
    }
}
