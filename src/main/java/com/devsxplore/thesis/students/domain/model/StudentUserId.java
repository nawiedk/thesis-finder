package com.devsxplore.thesis.students.domain.model;

public record StudentUserId(Long studentUserId) {
    public StudentUserId {
        if (studentUserId == null || studentUserId <= 0) {
            throw new IllegalArgumentException("User ID must be positive");
        }
    }

    public static StudentUserId of(Long value) {
        return new StudentUserId(value);
    }
}
