package com.congthanh.project.repository;

import com.congthanh.project.entity.Payment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PaymentRepository extends JpaRepository<Payment, String>, PaymentCustomRepository {
}
