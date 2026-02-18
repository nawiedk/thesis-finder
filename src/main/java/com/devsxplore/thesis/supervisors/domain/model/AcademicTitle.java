package com.devsxplore.thesis.supervisors.domain.model;

public enum AcademicTitle {

	NONE(""), DR("Dr."), PROF("Prof."), PROF_DR("Prof. Dr.");

	private final String abbreviation;

	AcademicTitle(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public static AcademicTitle findByString(String input) {
		if (input == null || input.isBlank()) {
			return NONE;
		}
		for (AcademicTitle title : values()) {
			if (title.name().equalsIgnoreCase(input) || title.abbreviation.equalsIgnoreCase(input)) {
				return title;
			}
		}
		return NONE;
	}

}
