package com.devsxplore.thesis.accounts.adapter.out.persistence;

import com.devsxplore.thesis.accounts.domain.model.UserAccount;

public class UserAccountMapper {

    public static UserAccount mapUserAccountToDomainEntity(UserAccountJDBCEntity entity) {
        return UserAccount.restore(
                entity.getGithubId(),
                entity.getLogin(),
                entity.getDisplayName(),
                entity.getRole(),
                entity.getLastLoginAt()
        );
    }

    public static UserAccountJDBCEntity mapUserAccountToJDBCEntity(UserAccount account) {
        UserAccountJDBCEntity entity = new UserAccountJDBCEntity();
        entity.setGithubId(account.getUserId());
        entity.setLogin(account.getLogin());
        entity.setDisplayName(account.getDisplayName());
        entity.setRole(account.getRoleAsString());
        entity.setLastLoginAt(account.getLastLoginAt());
        return entity;
    }
}
