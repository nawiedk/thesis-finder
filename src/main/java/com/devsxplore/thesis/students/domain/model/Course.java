package com.devsxplore.thesis.students.domain.model;

import java.util.Objects;

public record Course(String course) {
	public Course {
		Objects.requireNonNull(course, "Kursname darf nicht null sein");
		if (course.isBlank()) {
			throw new IllegalArgumentException("Kursname darf nicht leer sein");
		}
	}
}
