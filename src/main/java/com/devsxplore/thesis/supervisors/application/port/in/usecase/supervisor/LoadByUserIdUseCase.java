package com.devsxplore.thesis.supervisors.application.port.in.usecase.supervisor;

import com.devsxplore.thesis.supervisors.application.port.in.command.supervisor.LoadByUserIdCommand;
import com.devsxplore.thesis.supervisors.domain.model.Supervisor;

public interface LoadByUserIdUseCase {
    Supervisor loadSupervisor(LoadByUserIdCommand command);
}
