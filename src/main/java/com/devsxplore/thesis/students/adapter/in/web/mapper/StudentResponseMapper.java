package com.devsxplore.thesis.students.adapter.in.web.mapper;

import com.devsxplore.thesis.students.adapter.in.web.dto.response.UserProfileDTO;
import com.devsxplore.thesis.students.domain.model.Course;
import com.devsxplore.thesis.students.domain.model.Interest;
import com.devsxplore.thesis.students.domain.model.Student;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StudentResponseMapper {

	public UserProfileDTO mapToUserProfileDTO(Student student) {
		Set<String> courses = new HashSet<>();
		Set<String> interests = new HashSet<>();

		if (!student.getCourses().isEmpty())
			courses = student.getCourses().stream().map(Course::course).collect(Collectors.toSet());
		if (!student.getInterests().isEmpty())
			interests = student.getInterests().stream().map(Interest::interest).collect(Collectors.toSet());

		return new UserProfileDTO(student.getFirstName(), student.getLastName(), student.getFullName(), courses,
				interests);
	}

}
