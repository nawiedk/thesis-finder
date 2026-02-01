package com.devsxplore.thesis.profiles.application.port.in.usecase.supervisor;

import com.devsxplore.thesis.profiles.application.port.in.command.supervisor.FieldAddCommand;

public interface FieldAddUseCase {
    void addFieldToSupervisor(FieldAddCommand command);
}
