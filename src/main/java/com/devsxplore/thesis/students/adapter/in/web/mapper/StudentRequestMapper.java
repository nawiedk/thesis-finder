package com.devsxplore.thesis.students.adapter.in.web.mapper;

import com.devsxplore.thesis.students.adapter.in.web.dto.request.ChangeCourseDTO;
import com.devsxplore.thesis.students.adapter.in.web.dto.request.ChangeInterestDTO;
import com.devsxplore.thesis.students.adapter.in.web.dto.request.RegisterStudentDTO;
import com.devsxplore.thesis.students.adapter.in.web.dto.request.UpdateStudentProfileDTO;
import com.devsxplore.thesis.students.application.port.in.command.*;
import org.springframework.stereotype.Component;

@Component
public class StudentRequestMapper {

	public RegisterStudentCommand mapToRegisterStudentCommand(Long studentUserId, RegisterStudentDTO dto) {
		return new RegisterStudentCommand(studentUserId, dto.firstName(), dto.lastName());
	}

	public LoadStudentByUserIdCommand mapToLoadStudentByUserIdCommand(Long studenUserId) {
		return new LoadStudentByUserIdCommand(studenUserId);
	}

	public UpdateStudentProfileCommand mapToUpdateStudentProfileCommand(Long studentUserId,
			UpdateStudentProfileDTO dto) {
		return new UpdateStudentProfileCommand(studentUserId, dto.firstName(), dto.lastName());
	}

	public ChangeCourseCommand mapToChangeCourseCommand(Long studentUserId, ChangeCourseDTO dto) {
		return new ChangeCourseCommand(studentUserId, dto.course());
	}

	public ChangeInterestCommand mapToChangeInterestCommand(Long studentUserId, ChangeInterestDTO dto) {
		return new ChangeInterestCommand(studentUserId, dto.interest());
	}

}
