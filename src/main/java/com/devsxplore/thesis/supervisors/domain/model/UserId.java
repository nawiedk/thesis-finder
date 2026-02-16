package com.devsxplore.thesis.supervisors.domain.model;

public record UserId(Long userId) {
    public UserId {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be positive");
        }
    }

    public static UserId of(Long value) {
        return new UserId(value);
    }
}