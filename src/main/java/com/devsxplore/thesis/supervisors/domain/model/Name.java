package com.devsxplore.thesis.supervisors.domain.model;

import java.util.Objects;

public record Name(String firstName, String lastName, AcademicTitle title) {
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
		if (title == null)
			title = AcademicTitle.NONE;
	}

	public static Name nameFromPrimitive(String firstName, String lastName, String title) {
		return new Name(firstName, lastName, AcademicTitle.findByString(title));
	}

	public String getFullName() {
		if (title == AcademicTitle.NONE) {
			return firstName + " " + lastName;
		}
		return title.getAbbreviation() + " " + firstName + " " + lastName;
	}
}
