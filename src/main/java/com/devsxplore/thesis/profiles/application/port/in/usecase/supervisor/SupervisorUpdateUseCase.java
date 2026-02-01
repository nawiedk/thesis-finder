package com.devsxplore.thesis.profiles.application.port.in.usecase.supervisor;

import com.devsxplore.thesis.profiles.application.port.in.command.supervisor.SupervisorUpdateCommand;
import com.devsxplore.thesis.profiles.domain.model.Supervisor;

public interface SupervisorUpdateUseCase {
    Supervisor updateSupervisorProfile(SupervisorUpdateCommand command);
}
