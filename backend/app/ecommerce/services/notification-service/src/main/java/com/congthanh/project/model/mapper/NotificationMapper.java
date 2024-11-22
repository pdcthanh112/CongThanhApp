package com.congthanh.project.model.mapper;

import com.congthanh.project.model.dto.NotificationDTO;
import com.congthanh.project.model.entity.Notification;
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
