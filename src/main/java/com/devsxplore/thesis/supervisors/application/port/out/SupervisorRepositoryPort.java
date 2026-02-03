package com.devsxplore.thesis.supervisors.application.port.out;

import com.devsxplore.thesis.supervisors.domain.model.Supervisor;

import java.util.List;
import java.util.Optional;

public interface SupervisorRepositoryPort {
    Supervisor save(Supervisor supervisor);
    Optional<Supervisor> load(Long id);
    List<Supervisor> loadAll();
    boolean delete(Long supervisorId);
}
