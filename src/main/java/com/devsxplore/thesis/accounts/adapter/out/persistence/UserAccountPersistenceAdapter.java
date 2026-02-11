package com.devsxplore.thesis.accounts.adapter.out.persistence;

import com.devsxplore.thesis.accounts.application.port.out.UserAccountRepositoryPort;
import com.devsxplore.thesis.accounts.domain.model.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.devsxplore.thesis.accounts.adapter.out.persistence.UserAccountMapper.mapUserAccountToDomainEntity;
import static com.devsxplore.thesis.accounts.adapter.out.persistence.UserAccountMapper.mapUserAccountToJDBCEntity;

@Component
@RequiredArgsConstructor
public class UserAccountPersistenceAdapter implements UserAccountRepositoryPort {

    private final UserAccountRepository repository;

    @Override
    public UserAccount save(UserAccount account) {
        UserAccountJDBCEntity entity = mapUserAccountToJDBCEntity(account);
        UserAccountJDBCEntity saved = repository.save(entity);
        return mapUserAccountToDomainEntity(saved);
    }

    @Override
    public Optional<UserAccount> findById(Long githubId) {
        return repository.findById(githubId)
                .map(UserAccountMapper::mapUserAccountToDomainEntity);
    }

    @Override
    public List<UserAccount> findAll() {
        return repository.findAll()
                .stream()
                .map(UserAccountMapper::mapUserAccountToDomainEntity)
                .toList();
    }
}
