package com.congthanh.authservice.repository.account;

import com.congthanh.authservice.model.entity.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountCustomRepositoryImpl implements AccountCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Account findByEmailAndPassword(String email, String password) {
        String sql = "SELECT a FROM Account a WHERE a.email = :email AND a.password = :password";
        TypedQuery<Account> query = entityManager.createQuery(sql, Account.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        return query.getSingleResult();
    }

}
