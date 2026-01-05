package com.devsxplore.thesis.profiles.domain.model;

public record SupervisorId(Long id) {
    public SupervisorId {
        if (id != null && id <= 0)
            throw new IllegalArgumentException("Id must be positive");

    }

    public static SupervisorId unassigned() {
        return new SupervisorId(null);
    }
}
