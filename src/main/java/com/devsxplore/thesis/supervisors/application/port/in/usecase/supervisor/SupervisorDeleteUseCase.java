package com.devsxplore.thesis.supervisors.application.port.in.usecase.supervisor;

import com.devsxplore.thesis.supervisors.application.port.in.command.supervisor.SupervisorDeleteCommand;

public interface SupervisorDeleteUseCase {
    boolean deleteSupervisor(SupervisorDeleteCommand command);
}
