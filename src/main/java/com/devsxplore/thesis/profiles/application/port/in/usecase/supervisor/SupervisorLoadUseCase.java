package com.devsxplore.thesis.profiles.application.port.in.usecase.supervisor;

import com.devsxplore.thesis.profiles.application.port.in.command.supervisor.SupervisorLoadCommand;
import com.devsxplore.thesis.profiles.domain.model.Supervisor;

public interface SupervisorLoadUseCase {
    Supervisor loadSupervisorById(SupervisorLoadCommand command);
}
