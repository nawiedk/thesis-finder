package com.devsxplore.thesis.profiles.application.port.in.usecase;

import com.devsxplore.thesis.profiles.application.port.in.command.CreateSupervisorCommand;
import com.devsxplore.thesis.profiles.domain.model.Supervisor;

public interface CreateSupervisorUseCase {
    Supervisor createSupervisor(CreateSupervisorCommand command);
}
