package com.congthanh.project.repository.orderDetail;

import com.congthanh.project.model.entity.OrderDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface OrderDetailCustomRepository {

    Page<OrderDetail> findByStatus(String status, Pageable pageable);

}
