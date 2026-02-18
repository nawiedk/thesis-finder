package com.devsxplore.thesis.accounts.application.service;

import com.devsxplore.thesis.accounts.application.config.AccountSecurityProperties;
import com.devsxplore.thesis.accounts.application.port.in.command.AssignUserRoleCommand;
import com.devsxplore.thesis.accounts.application.port.in.command.LoadAccountCommand;
import com.devsxplore.thesis.accounts.application.port.in.command.RegisterAccountCommand;
import com.devsxplore.thesis.accounts.application.port.in.usecase.AssignUserRoleUseCase;
import com.devsxplore.thesis.accounts.application.port.in.usecase.ListAccountsUseCase;
import com.devsxplore.thesis.accounts.application.port.in.usecase.LoadAccountUseCase;
import com.devsxplore.thesis.accounts.application.port.in.usecase.RegisterOrUpdateAccountUseCase;
import com.devsxplore.thesis.accounts.application.port.out.UserAccountRepositoryPort;
import com.devsxplore.thesis.accounts.domain.model.UserAccount;
import com.devsxplore.thesis.accounts.domain.model.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.devsxplore.thesis.accounts.domain.model.UserAccount.createUserAccountWithId;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAccountService
		implements AssignUserRoleUseCase, ListAccountsUseCase, LoadAccountUseCase, RegisterOrUpdateAccountUseCase {

	private final UserAccountRepositoryPort userAccountRepositoryPort;

	private final AccountSecurityProperties accountSecurityProperties;

	@Override
	public void assignRole(AssignUserRoleCommand command) {
		UserAccount account = userAccountRepositoryPort.findById(command.githubId())
			.orElseThrow(() -> new IllegalArgumentException("User not found"));
		account.assignRole(command.role());
		userAccountRepositoryPort.save(account);
	}

	@Override
	public List<UserAccount> loadAll() {
		return userAccountRepositoryPort.findAll();
	}

	@Override
	public UserAccount loadById(LoadAccountCommand command) {
		return userAccountRepositoryPort.findById(command.githubId())
			.orElseThrow(() -> new IllegalArgumentException("User not found"));
	}

	@Override
	public UserAccount registerOrUpdate(RegisterAccountCommand command) {
		UserAccount account = userAccountRepositoryPort.findById(command.githubId())
			.orElseGet(() -> createUserAccountWithId(command.githubId(), command.login(), command.displayName()));
		account.updateProfile(command.login(), command.displayName());
		if (accountSecurityProperties.isAdmin(account.getUserId())) {
			account.assignRole(UserRole.ADMIN);
		}
		return userAccountRepositoryPort.save(account);
	}

}
