package com.congthanh.project.repository.management;

import com.congthanh.project.entity.management.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Long> {

  Optional<Account> findByEmail(String email);
}
