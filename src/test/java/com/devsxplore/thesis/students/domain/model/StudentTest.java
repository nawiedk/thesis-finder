package com.devsxplore.thesis.students.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudentTest {

	@Test
	void should_create_student_and_add_courses_uniquely() {
		Student student = Student.createStudentWithoutId(StudentUserId.of(1L), new Name("Max", "Mustermann"));

		student.addCourse("Mathe 1");
		student.addCourse("mathe 1");
		student.addCourse("  Mathe 1  ");

		assertThat(student.getCourses()).hasSize(1);
		assertThat(student.containsCourse("Mathe 1")).isTrue();
	}

	@Test
	void should_update_profile_only_for_non_blank_values() {
		Student student = Student.createStudentWithoutId(StudentUserId.of(1L), new Name("Max", "Mustermann"));

		student.updateProfile("", "  ");
		assertThat(student.getFirstName()).isEqualTo("Max");
		assertThat(student.getLastName()).isEqualTo("Mustermann");

		student.updateProfile("Moritz", null);
		assertThat(student.getFirstName()).isEqualTo("Moritz");
		assertThat(student.getLastName()).isEqualTo("Mustermann");
	}

	@Test
	void should_handle_interest_removal_correctly() {
		Student student = Student.createStudentWithoutId(StudentUserId.of(1L), new Name("A", "B"));
		student.addInterest("Java");

		assertThat(student.removeInterest("Java")).isTrue();
		assertThat(student.removeInterest("Python")).isFalse();
		assertThat(student.removeInterest("")).isFalse();
	}

}