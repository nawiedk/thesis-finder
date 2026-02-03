package com.devsxplore.thesis.supervisors.domain.model;

public record FieldTag(String fieldName) {
    public FieldTag {
        if (fieldName == null || fieldName.isBlank())
            throw new IllegalArgumentException("Name cannot be null or blank");
        if (fieldName.length() > 50)
            throw new IllegalArgumentException("Name cannot exceed 50 characters");
        fieldName = fieldName.trim();
    }
}
