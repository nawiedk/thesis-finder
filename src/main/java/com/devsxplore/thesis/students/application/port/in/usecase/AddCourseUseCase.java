package com.devsxplore.thesis.students.application.port.in.usecase;

import com.devsxplore.thesis.students.application.port.in.command.ChangeCourseCommand;
import com.devsxplore.thesis.students.domain.model.Student;

public interface AddCourseUseCase {

	Student addCourse(ChangeCourseCommand command);

}
