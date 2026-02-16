package com.devsxplore.thesis.students.domain.model;

import java.util.Objects;

public record Interest(String interest) {
    public Interest {
        Objects.requireNonNull(interest, "Interesse darf nicht null sein");
        if (interest.isBlank()) {
            throw new IllegalArgumentException("Interesse darf nicht leer sein");
        }
    }
}
