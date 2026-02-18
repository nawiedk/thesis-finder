package com.devsxplore.thesis.supervisors.application.port.in.usecase.supervisor;

import com.devsxplore.thesis.supervisors.application.port.in.command.supervisor.SupervisorCreateCommand;
import com.devsxplore.thesis.supervisors.domain.model.Supervisor;

public interface SupervisorCreateUseCase {

	Supervisor createSupervisor(SupervisorCreateCommand command);

}
