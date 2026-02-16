package com.devsxplore.thesis.students.domain.model;

import java.util.Objects;

public record Name(String firstName, String lastName) {
    public Name {
        Objects.requireNonNull(firstName, "Vorname darf nicht null sein");
        Objects.requireNonNull(lastName, "Nachname darf nicht null sein");

        if (firstName.isBlank()) {
            throw new IllegalArgumentException("Vorname darf nicht leer sein");
        }
        if (lastName.isBlank()) {
            throw new IllegalArgumentException("Nachname darf nicht leer sein");
        }
        firstName = firstName.trim();
        lastName = lastName.trim();
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
