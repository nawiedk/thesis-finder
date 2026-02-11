package com.devsxplore.thesis.accounts.adapter.out.persistence;

import com.devsxplore.thesis.accounts.domain.model.UserAccount;

public class UserAccountMapper {

    public static UserAccount mapUserAccountToDomainEntity(UserAccountJDBCEntity entity) {
        return UserAccount.restore(
                entity.githubId(),
                entity.login(),
                entity.displayName(),
                entity.role(),
                entity.lastLoginAt()
        );
    }

    public static UserAccountJDBCEntity mapUserAccountToJDBCEntity(UserAccount account) {
        return new UserAccountJDBCEntity(
                account.getUserId(),
                account.getLogin(),
                account.getDisplayName(),
                account.getRole().name(),
                account.getLastLoginAt()
        );
    }
}
