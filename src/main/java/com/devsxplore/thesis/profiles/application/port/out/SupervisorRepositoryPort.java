package com.devsxplore.thesis.profiles.application.port.out;

import com.devsxplore.thesis.profiles.domain.model.Supervisor;

import java.util.List;
import java.util.Optional;

public interface SupervisorRepositoryPort {
    Supervisor save(Supervisor supervisor);
    Optional<Supervisor> load(Long id);
    List<Supervisor> loadAll();
    boolean delete(Long supervisorId);
}
