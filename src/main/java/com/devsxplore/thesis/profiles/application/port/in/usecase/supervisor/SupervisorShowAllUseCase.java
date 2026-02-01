package com.devsxplore.thesis.profiles.application.port.in.usecase.supervisor;

import com.devsxplore.thesis.profiles.domain.model.Supervisor;

import java.util.List;

public interface SupervisorShowAllUseCase {
    List<Supervisor> showAllSupervisors();
}
