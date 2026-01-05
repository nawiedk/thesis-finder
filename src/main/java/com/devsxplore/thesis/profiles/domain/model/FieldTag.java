package com.devsxplore.thesis.profiles.domain.model;

public record FieldTag(String name) {
    public FieldTag {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be null or blank");
        if (name.length() > 50)
            throw new IllegalArgumentException("Name cannot exceed 50 characters");
        name = name.trim();
    }
}
