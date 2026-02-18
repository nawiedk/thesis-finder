package com.devsxplore.thesis.students.application.port.in.usecase;

import com.devsxplore.thesis.students.application.port.in.command.LoadStudentCommand;
import com.devsxplore.thesis.students.domain.model.Student;

public interface LoadStudentUseCase {

	Student loadStudent(LoadStudentCommand command);

}
