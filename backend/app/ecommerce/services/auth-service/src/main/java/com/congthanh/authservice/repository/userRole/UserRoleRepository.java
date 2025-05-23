package com.congthanh.authservice.repository.userRole;

import com.congthanh.authservice.model.entity.Account;
import com.congthanh.authservice.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>, UserRoleCustomRepository {
    List<UserRole> findByAccount(Account account);
}
