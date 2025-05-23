package com.congthanh.authservice.repository.userRole;

import com.congthanh.authservice.model.entity.UserRole;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRoleCustomRepositoryImpl implements UserRoleCustomRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<String> getRoleByAccountId(String accountId) {
//        String sql = "SELECT u FROM UserRole u WHERE u.account.accountId = :accountId";
//        TypedQuery<UserRole> query = entityManager.createQuery(sql, UserRole.class);
//        query.setParameter("accountId", accountId);
//        List<UserRole> userRoles = query.getResultList();
//        if (!userRoles.isEmpty()) {
//            List<String> roles = userRoles.stream().map(ur -> ur.getRole().getDescription()).collect(Collectors.toList());
//            return roles;
//        }
//        return List.of();
        return List.of("ROLE_USER", "ROLE_ADMIN");
    }
}
