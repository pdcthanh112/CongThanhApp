package com.congthanh.authservice.repository.account;

import com.congthanh.authservice.model.entity.Account;

public interface AccountCustomRepository {

    Account findByEmailAndPassword(String email, String password);
}
