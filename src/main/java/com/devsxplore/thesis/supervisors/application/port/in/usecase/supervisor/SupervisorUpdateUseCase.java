package com.devsxplore.thesis.supervisors.application.port.in.usecase.supervisor;

import com.devsxplore.thesis.supervisors.application.port.in.command.supervisor.SupervisorUpdateCommand;
import com.devsxplore.thesis.supervisors.domain.model.Supervisor;

public interface SupervisorUpdateUseCase {
    Supervisor updateSupervisorProfile(SupervisorUpdateCommand command);
}
