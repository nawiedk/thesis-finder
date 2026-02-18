package com.devsxplore.thesis.accounts.application.port.out;

import com.devsxplore.thesis.accounts.domain.model.UserAccount;

import java.util.List;
import java.util.Optional;

public interface UserAccountRepositoryPort {

	UserAccount save(UserAccount account);

	Optional<UserAccount> findById(Long githubId);

	List<UserAccount> findAll();

}
