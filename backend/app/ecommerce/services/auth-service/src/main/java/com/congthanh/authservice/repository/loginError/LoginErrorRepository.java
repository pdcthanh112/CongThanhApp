package com.congthanh.authservice.repository.loginError;

import com.congthanh.authservice.model.entity.LoginError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginErrorRepository extends JpaRepository<LoginError, Long> {
    LoginError findByAccountId(String accountId);
}
