package com.devsxplore.thesis.students.domain.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Student {

	private final StudentId studentId;

	private final StudentUserId studentUserId;

	private final Set<Course> courses;

	private final Set<Interest> interests;

	private Name name;

	private Student(StudentId studentId, StudentUserId studentUserId, Name name) {
		this.studentId = Objects.requireNonNull(studentId, "Student ID cannot be null");
		this.studentUserId = Objects.requireNonNull(studentUserId, "User ID cannot be null");
		this.name = Objects.requireNonNull(name);
		this.courses = new HashSet<>();
		this.interests = new HashSet<>();
	}

	public static Student createStudentWithoutId(StudentUserId studentUserId, Name name) {
		return new Student(StudentId.unassigned(), studentUserId, name);
	}

	public static Student createStudentWithId(StudentId studentId, StudentUserId studentUserId, Name name) {
		return new Student(studentId, studentUserId, name);
	}

	public Long getStudentId() {
		return studentId.studentId();
	}

	public Long getStudentUserId() {
		return studentUserId.studentUserId();
	}

	public String getFirstName() {
		return name.firstName();
	}

	public String getLastName() {
		return name.lastName();
	}

	public String getFullName() {
		return name.getFullName();
	}

	public Set<Course> getCourses() {
		return Set.copyOf(courses);
	}

	public void setCourses(Set<Course> newCourses) {
		courses.addAll(newCourses);
	}

	public boolean containsCourse(String course) {
		if (courses.isEmpty())
			return false;
		return courses.stream().anyMatch(c -> c.course().trim().equalsIgnoreCase(course.trim()));
	}

	public boolean containsInterest(String interest) {
		if (interests.isEmpty())
			return false;
		return interests.stream().anyMatch(i -> i.interest().trim().equalsIgnoreCase(interest.trim()));
	}

	public Set<Interest> getInterests() {
		return Set.copyOf(interests);
	}

	public void setInterests(Set<Interest> newInterests) {
		interests.addAll(newInterests);
	}

	public void updateProfile(String firstName, String lastName) {
		this.name = new Name((firstName == null || firstName.isBlank()) ? name.firstName() : firstName,
				(lastName == null || lastName.isBlank()) ? name.lastName() : lastName);
	}

	public void addCourse(String course) {
		if (course != null && !course.isBlank() && !containsCourse(course)) {
			courses.add(new Course(course));
		}
	}

	public boolean removeCourse(String course) {
		if (course == null || course.isBlank())
			return false;
		return courses.removeIf(value -> value.course().trim().equalsIgnoreCase(course.trim()));
	}

	public boolean removeInterest(String interest) {
		if (interest == null || interest.isBlank())
			return false;
		return interests.removeIf(value -> value.interest().trim().equalsIgnoreCase(interest.trim()));
	}

	public void addInterest(String interestName) {
		if (interestName != null && !interestName.isBlank() && !containsInterest(interestName)) {
			interests.add(new Interest(interestName));
		}
	}

}
