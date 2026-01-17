package com.devsxplore.thesis.profiles.application.port.out;

import com.devsxplore.thesis.profiles.domain.model.Supervisor;

public interface SupervisorRepositoryPort {
    Supervisor save(Supervisor supervisor);
    Supervisor load(Long id);
}
