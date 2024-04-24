package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.ecommerce.NotificationDTO;
import com.congthanh.project.entity.ecommerce.Notification;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    private void configureModelMapper() {

    }

    public Notification mapNotificationDTOToEntity(NotificationDTO notificationDTO) {
        return modelMapper.map(notificationDTO, Notification.class);
    }

    public NotificationDTO mapNotificationEntityToDTO(Notification notification) {
        return modelMapper.map(notification, NotificationDTO.class);
    }
}
