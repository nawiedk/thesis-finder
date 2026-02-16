package com.devsxplore.thesis.students.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudentDomainBranchTest {

    @Test
    void addCourse_should_ignore_null_or_blank_input() {
        Student student = Student.createStudentWithoutId(StudentUserId.of(1L), new Name("Max", "Mustermann"));

        student.addCourse(null);
        student.addCourse("");
        student.addCourse("   ");

        assertThat(student.getCourses()).isEmpty();
    }

    @Test
    void containsCourse_should_return_false_when_list_is_empty() {
        Student student = Student.createStudentWithoutId(StudentUserId.of(1L), new Name("Max", "Mustermann"));
        assertThat(student.containsCourse("Java")).isFalse();
    }

    @Test
    void updateProfile_should_keep_existing_name_when_inputs_are_blank() {
        Student student = Student.createStudentWithoutId(StudentUserId.of(1L), new Name("Max", "Mustermann"));

        student.updateProfile(null, "");
        assertThat(student.getFirstName()).isEqualTo("Max");
        assertThat(student.getLastName()).isEqualTo("Mustermann");

        student.updateProfile("  ", "NeuerNachname");
        assertThat(student.getFirstName()).isEqualTo("Max");
        assertThat(student.getLastName()).isEqualTo("NeuerNachname");
    }

    @Test
    void removeCourse_should_return_false_on_invalid_input_or_missing_course() {
        Student student = Student.createStudentWithoutId(StudentUserId.of(1L), new Name("Max", "Mustermann"));
        student.addCourse("Java");

        assertThat(student.removeCourse(null)).isFalse();
        assertThat(student.removeCourse("  ")).isFalse();
        assertThat(student.removeCourse("Python")).isFalse();
        assertThat(student.removeCourse("JAVA")).isTrue();
    }
}