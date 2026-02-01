package com.devsxplore.thesis.profiles.domain.model;

public record SupervisorId(Long supervisorId) {
    public SupervisorId {
        if (supervisorId != null && supervisorId <= 0)
            throw new IllegalArgumentException("Id must be positive");

    }

    public static SupervisorId unassigned() {
        return new SupervisorId(null);
    }
}
