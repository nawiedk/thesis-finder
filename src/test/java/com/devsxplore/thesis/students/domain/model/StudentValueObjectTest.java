package com.devsxplore.thesis.students.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StudentValueObjectTest {

	@Test
	void name_constructor_should_throw_exception_on_blank_values() {
		assertThatThrownBy(() -> new Name("", "Mustermann")).isInstanceOf(IllegalArgumentException.class);

		assertThatThrownBy(() -> new Name("Max", "  ")).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void studentUserId_should_reject_null_or_negative_values() {
		assertThatThrownBy(() -> new StudentUserId(null)).isInstanceOf(IllegalArgumentException.class);

		assertThatThrownBy(() -> new StudentUserId(0L)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void course_and_interest_should_reject_blank_strings() {
		assertThatThrownBy(() -> new Course(" ")).isInstanceOf(IllegalArgumentException.class);

		assertThatThrownBy(() -> new Interest("")).isInstanceOf(IllegalArgumentException.class);
	}

}