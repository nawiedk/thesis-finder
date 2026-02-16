package com.devsxplore.thesis.students.application.port.in.usecase;

import com.devsxplore.thesis.students.application.port.in.command.RegisterStudentCommand;
import com.devsxplore.thesis.students.domain.model.Student;

public interface RegisterStudentUseCase {
    Student registerStudent(RegisterStudentCommand command);
}
