package com.congthanh.authservice.repository.userRole;

import java.util.List;

public interface UserRoleCustomRepository {

    List<String> getRoleByAccountId(String accountId);
}
