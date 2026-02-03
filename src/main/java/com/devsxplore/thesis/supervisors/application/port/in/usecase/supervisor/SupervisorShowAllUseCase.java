package com.devsxplore.thesis.supervisors.application.port.in.usecase.supervisor;

import com.devsxplore.thesis.supervisors.domain.model.Supervisor;

import java.util.List;

public interface SupervisorShowAllUseCase {
    List<Supervisor> showAllSupervisors();
}
