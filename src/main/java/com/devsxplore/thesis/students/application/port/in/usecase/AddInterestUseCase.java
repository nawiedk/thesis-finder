package com.devsxplore.thesis.students.application.port.in.usecase;

import com.devsxplore.thesis.students.application.port.in.command.ChangeInterestCommand;
import com.devsxplore.thesis.students.domain.model.Student;

public interface AddInterestUseCase {
    Student addInterest(ChangeInterestCommand command);
}
