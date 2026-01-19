package com.devsxplore.thesis.profiles.domain.model;

public record Name(String firstName, String lastName, AcademicTitle title) {
    public Name {
        if (firstName == null || firstName.isBlank())
            throw new IllegalArgumentException("First name cannot be null or empty");
        if (lastName == null || lastName.isBlank())
            throw new IllegalArgumentException("Last name cannot be null or empty");
        firstName = firstName.trim();
        lastName = lastName.trim();
        if (title == null)
            title = AcademicTitle.NONE;
    }

    public static Name nameFromPrimitive(String firstName, String lastName, String title){
        return new Name(firstName, lastName, AcademicTitle.findByString(title));
    }

    public String getFullName() {
        if (title == AcademicTitle.NONE) {
            return firstName + " " + lastName;
        }
        return title.getAbbreviation() + " " + firstName + " " + lastName;
    }
}
