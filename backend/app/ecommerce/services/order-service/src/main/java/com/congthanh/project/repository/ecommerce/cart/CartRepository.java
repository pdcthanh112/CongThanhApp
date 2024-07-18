package com.congthanh.project.repository.ecommerce.cart;

import com.congthanh.project.constant.common.StateStatus;
import com.congthanh.project.entity.ecommerce.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CartRepository extends JpaRepository<Cart, String>, CartCustomRepository {

  @Query(nativeQuery = true, value = "SELECT * FROM cart WHERE customerid = ?1 AND status = '" + StateStatus.CHECKED_OUT + "' ORDER BY created_date desc")
  List<Cart> findHistoryByCustomerId(String customerId);

}
