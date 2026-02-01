package com.devsxplore.thesis.profiles.application.port.in.usecase.supervisor;

import com.devsxplore.thesis.profiles.application.port.in.command.supervisor.SupervisorCreateCommand;
import com.devsxplore.thesis.profiles.domain.model.Supervisor;

public interface SupervisorCreateUseCase {
    Supervisor createSupervisor(SupervisorCreateCommand command);
}
