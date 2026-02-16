package com.devsxplore.thesis.supervisors.domain.model;

import java.util.UUID;

public record PublicId(UUID uuid) {
    public static PublicId generate() {
        return new PublicId(UUID.randomUUID());
    }
}
