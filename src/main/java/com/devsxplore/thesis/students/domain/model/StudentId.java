package com.devsxplore.thesis.students.domain.model;

public record StudentId(Long studentId) {
	public StudentId {
		if (studentId != null && studentId <= 0) {
			throw new IllegalArgumentException("Student ID must be positive");
		}
	}

	public static StudentId unassigned() {
		return new StudentId(null);
	}

	public static StudentId of(Long studentId) {
		return new StudentId(studentId);
	}
}