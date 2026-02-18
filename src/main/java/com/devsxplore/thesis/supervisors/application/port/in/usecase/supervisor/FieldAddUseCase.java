package com.devsxplore.thesis.supervisors.application.port.in.usecase.supervisor;

import com.devsxplore.thesis.supervisors.application.port.in.command.supervisor.FieldAddCommand;

public interface FieldAddUseCase {

	void addFieldToSupervisor(FieldAddCommand command);

}
