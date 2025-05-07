package com.congthanh.notificationservice.repository.webpushSubscription;

import com.congthanh.notificationservice.model.entity.WebpushSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebpushSubscriptionRepository extends JpaRepository<WebpushSubscription, Long> {

    boolean existsByUserId(String userId);

    WebpushSubscription findByUserId(String userId);
}
