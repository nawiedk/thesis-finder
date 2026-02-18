package com.devsxplore.thesis.students.adapter.out.persistence.mapper;

import com.devsxplore.thesis.students.adapter.out.persistence.jdbcentity.StudentJDBCEntity;
import com.devsxplore.thesis.students.domain.model.Name;
import com.devsxplore.thesis.students.domain.model.Student;
import com.devsxplore.thesis.students.domain.model.StudentId;
import com.devsxplore.thesis.students.domain.model.StudentUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.devsxplore.thesis.students.domain.model.Student.createStudentWithId;

@Component
@RequiredArgsConstructor
public class StudentMapper {

	private final CourseMapper courseMapper;

	private final InterestMapper interestMapper;

	public Student mapStudentToDomainEntity(StudentJDBCEntity entity) {
		Student student = createStudentWithId(StudentId.of(entity.studentId()),
				StudentUserId.of(entity.studentUserId()), new Name(entity.firstName(), entity.lastName()));
		student.setCourses(courseMapper.mapCoursesToDomainEntities(entity.courses()));
		student.setInterests(interestMapper.mapInterestsToDomainEntities(entity.interests()));
		return student;
	}

	public StudentJDBCEntity mapStudentToJDBCEntity(Student student) {
		return new StudentJDBCEntity(student.getStudentId(), student.getStudentUserId(), student.getFirstName(),
				student.getLastName(), courseMapper.mapCoursesToJDBCEntities(student.getCourses()),
				interestMapper.mapInterestsToJDBCEntities(student.getInterests()));
	}

}
