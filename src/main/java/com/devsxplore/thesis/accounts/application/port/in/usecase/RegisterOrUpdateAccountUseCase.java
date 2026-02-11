package com.devsxplore.thesis.accounts.application.port.in.usecase;

import com.devsxplore.thesis.accounts.application.port.in.command.RegisterAccountCommand;
import com.devsxplore.thesis.accounts.domain.model.UserAccount;

public interface RegisterOrUpdateAccountUseCase {
    UserAccount registerOrUpdate(RegisterAccountCommand command);
}
