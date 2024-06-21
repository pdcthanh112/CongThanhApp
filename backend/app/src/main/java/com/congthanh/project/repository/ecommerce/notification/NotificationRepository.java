package com.congthanh.project.repository.ecommerce.notification;

import com.congthanh.project.entity.ecommerce.Notification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationCustomRepository {

  @Query(nativeQuery = true, value = "SELECT * FROM notification WHERE customer = ?1 AND status = 'ACTIVE' ORDER BY created_at DESC")
  List<Notification> getNotificationByCustomer(String customerId);

}
