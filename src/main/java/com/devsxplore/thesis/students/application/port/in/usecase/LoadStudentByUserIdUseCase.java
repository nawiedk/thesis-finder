package com.devsxplore.thesis.students.application.port.in.usecase;

import com.devsxplore.thesis.students.application.port.in.command.LoadStudentByUserIdCommand;
import com.devsxplore.thesis.students.domain.model.Student;

public interface LoadStudentByUserIdUseCase {

	Student loadStudentByStudentUserId(LoadStudentByUserIdCommand command);

}
