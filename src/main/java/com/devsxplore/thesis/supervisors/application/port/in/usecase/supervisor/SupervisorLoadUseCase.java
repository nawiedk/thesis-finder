package com.devsxplore.thesis.supervisors.application.port.in.usecase.supervisor;

import com.devsxplore.thesis.supervisors.application.port.in.command.supervisor.SupervisorLoadCommand;
import com.devsxplore.thesis.supervisors.domain.model.Supervisor;

public interface SupervisorLoadUseCase {
    Supervisor loadSupervisorById(SupervisorLoadCommand command);
}
