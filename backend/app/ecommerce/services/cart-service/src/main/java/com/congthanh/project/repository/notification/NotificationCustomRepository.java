package com.congthanh.project.repository.notification;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface NotificationCustomRepository {

    @Modifying
    boolean changeNotificationReadStatus(Long notificationId, boolean status);

}
