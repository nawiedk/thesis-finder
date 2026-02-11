package com.devsxplore.thesis.accounts.application.port.in.usecase;

import com.devsxplore.thesis.accounts.domain.model.UserAccount;

import java.util.List;

public interface ListAccountsUseCase {
    List<UserAccount> loadAll();
}
