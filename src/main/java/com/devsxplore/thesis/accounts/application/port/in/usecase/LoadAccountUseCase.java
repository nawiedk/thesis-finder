package com.devsxplore.thesis.accounts.application.port.in.usecase;

import com.devsxplore.thesis.accounts.application.port.in.command.LoadAccountCommand;
import com.devsxplore.thesis.accounts.domain.model.UserAccount;

public interface LoadAccountUseCase {

	UserAccount loadById(LoadAccountCommand command);

}
