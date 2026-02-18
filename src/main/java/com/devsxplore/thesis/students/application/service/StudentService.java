package com.devsxplore.thesis.students.application.service;

import com.devsxplore.thesis.accounts.application.port.in.command.AssignUserRoleCommand;
import com.devsxplore.thesis.accounts.application.port.in.usecase.AssignUserRoleUseCase;
import com.devsxplore.thesis.accounts.domain.model.UserRole;
import com.devsxplore.thesis.students.application.port.in.command.*;
import com.devsxplore.thesis.students.application.port.in.usecase.*;
import com.devsxplore.thesis.students.application.port.out.StudentRepositoryPort;
import com.devsxplore.thesis.students.domain.model.Name;
import com.devsxplore.thesis.students.domain.model.Student;
import com.devsxplore.thesis.students.domain.model.StudentUserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.devsxplore.thesis.students.domain.model.Student.createStudentWithoutId;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService implements RegisterStudentUseCase, LoadStudentUseCase, LoadStudentByUserIdUseCase,
		AddCourseUseCase, UpdateStudentProfileUseCase, RemoveCourseUseCase, AddInterestUseCase, RemoveInterestUseCase {

	private final StudentRepositoryPort studentRepositoryPort;

	private final AssignUserRoleUseCase assignUserRoleUseCase;

	@Override
	public Student registerStudent(RegisterStudentCommand command) {
		if (studentRepositoryPort.existsByStudentUserId(command.studentUserId()))
			throw new IllegalArgumentException("Du bist bereits registriert!");
		Student student = createStudentWithoutId(StudentUserId.of(command.studentUserId()),
				new Name(command.firstName(), command.lastName()));

		Student savedStudent = studentRepositoryPort.save(student);

		assignUserRoleUseCase.assignRole(new AssignUserRoleCommand(command.studentUserId(), UserRole.STUDENT));

		return savedStudent;
	}

	@Override
	public Student loadStudentByStudentUserId(LoadStudentByUserIdCommand command) {
		return studentRepositoryPort.loadByStudentUserId(command.studentUserId())
			.orElseThrow(() -> new IllegalArgumentException("Student not found"));
	}

	@Override
	public Student loadStudent(LoadStudentCommand command) {
		return studentRepositoryPort.load(command.studentId())
			.orElseThrow(() -> new IllegalArgumentException("Student not found"));
	}

	@Override
	public Student addCourse(ChangeCourseCommand command) {
		Student student = studentRepositoryPort.loadByStudentUserId(command.studentUserId())
			.orElseThrow(() -> new IllegalArgumentException("Student not found"));
		student.addCourse(command.course());
		return studentRepositoryPort.save(student);
	}

	@Override
	public Student updateProfile(UpdateStudentProfileCommand command) {
		Student student = studentRepositoryPort.loadByStudentUserId(command.studentUserId())
			.orElseThrow(() -> new IllegalArgumentException("Student not found"));
		student.updateProfile(command.firstName(), command.lastName());
		return studentRepositoryPort.save(student);
	}

	@Override
	public Student removeCourse(ChangeCourseCommand command) {
		Student student = studentRepositoryPort.loadByStudentUserId(command.studentUserId())
			.orElseThrow(() -> new IllegalArgumentException("Student not found"));
		student.removeCourse(command.course());
		return studentRepositoryPort.save(student);
	}

	@Override
	public Student addInterest(ChangeInterestCommand command) {
		Student student = studentRepositoryPort.loadByStudentUserId(command.studentUserId())
			.orElseThrow(() -> new IllegalArgumentException("Student not found"));
		student.addInterest(command.interest());
		return studentRepositoryPort.save(student);
	}

	@Override
	public Student removeInterest(ChangeInterestCommand command) {
		Student student = studentRepositoryPort.loadByStudentUserId(command.studentUserId())
			.orElseThrow(() -> new IllegalArgumentException("Student not found"));
		student.removeInterest(command.interest());
		return studentRepositoryPort.save(student);
	}

}
