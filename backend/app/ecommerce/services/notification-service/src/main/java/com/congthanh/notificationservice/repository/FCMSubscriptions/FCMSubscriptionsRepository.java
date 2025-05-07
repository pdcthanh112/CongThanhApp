package com.congthanh.notificationservice.repository.FCMSubscriptions;

import com.congthanh.notificationservice.model.entity.FCMSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FCMSubscriptionsRepository extends JpaRepository<FCMSubscription, Long> {

    FCMSubscription findByUserId(String userId);
}
