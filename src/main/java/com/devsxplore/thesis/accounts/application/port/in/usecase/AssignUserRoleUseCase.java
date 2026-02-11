package com.devsxplore.thesis.accounts.application.port.in.usecase;

import com.devsxplore.thesis.accounts.application.port.in.command.AssignUserRoleCommand;

public interface AssignUserRoleUseCase {
    void assignRole(AssignUserRoleCommand command);
}
