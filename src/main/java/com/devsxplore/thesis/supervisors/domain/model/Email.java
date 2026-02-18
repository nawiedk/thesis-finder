package com.devsxplore.thesis.supervisors.domain.model;

import java.util.regex.Pattern;

public record Email(String email) {

	private static final Pattern EMAIL_PATTERN = Pattern
		.compile("^[A-Za-z0-9._%+-]+@(hhu\\.de|uni-duesseldorf\\.de|med\\.uni-duesseldorf\\.de)$");

	public Email {
		if (email == null || email.isBlank())
			throw new IllegalArgumentException("email cannot be null or blank");
		email = email.trim().toLowerCase();
		if (!EMAIL_PATTERN.matcher(email).matches())
			throw new IllegalArgumentException("Invalid email domain");
	}
}
