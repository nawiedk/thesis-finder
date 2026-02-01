package com.devsxplore.thesis.profiles.application.port.in.usecase.supervisor;

import com.devsxplore.thesis.profiles.application.port.in.command.supervisor.SupervisorDeleteCommand;

public interface SupervisorDeleteUseCase {
    boolean deleteSupervisor(SupervisorDeleteCommand command);
}
