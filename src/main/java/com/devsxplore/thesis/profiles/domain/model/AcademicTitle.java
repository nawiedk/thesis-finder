package com.devsxplore.thesis.profiles.domain.model;

public enum AcademicTitle {
    NONE(""),
    DR("Dr."),
    PROF("Prof."),
    PROF_DR("Prof. Dr.");

    private final String abbreviation;

    AcademicTitle(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
