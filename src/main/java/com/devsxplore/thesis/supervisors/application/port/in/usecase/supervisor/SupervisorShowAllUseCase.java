package com.devsxplore.thesis.supervisors.application.port.in.usecase.supervisor;

import java.util.Set;
import com.devsxplore.thesis.supervisors.domain.model.Supervisor;

public interface SupervisorShowAllUseCase {

	Set<Supervisor> showAllSupervisors();

}
