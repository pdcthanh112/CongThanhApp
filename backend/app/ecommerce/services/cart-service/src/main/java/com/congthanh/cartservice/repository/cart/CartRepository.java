package com.congthanh.cartservice.repository.cart;

import com.congthanh.cartservice.constant.common.StateStatus;
import com.congthanh.cartservice.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>, CartCustomRepository {

  @Query(nativeQuery = true, value = "SELECT * FROM cart WHERE customerid = ?1 AND status = '" + StateStatus.CHECKED_OUT + "' ORDER BY created_date desc")
  List<Cart> findHistoryByCustomerId(String customerId);

}
