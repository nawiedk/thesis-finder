package com.devsxplore.thesis.supervisors.application.port.in.usecase.supervisor;

import com.devsxplore.thesis.supervisors.application.port.in.command.supervisor.LoadByPublicIdCommand;
import com.devsxplore.thesis.supervisors.domain.model.Supervisor;

public interface LoadByPublicIdUseCase {
	Supervisor load(LoadByPublicIdCommand command);
}
