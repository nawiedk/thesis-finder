package com.devsxplore.thesis.students.application.port.in.usecase;

import com.devsxplore.thesis.students.application.port.in.command.UpdateStudentProfileCommand;
import com.devsxplore.thesis.students.domain.model.Student;

public interface UpdateStudentProfileUseCase {

	Student updateProfile(UpdateStudentProfileCommand command);

}
