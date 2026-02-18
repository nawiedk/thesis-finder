package com.devsxplore.thesis.students.application.port.in.usecase;

import com.devsxplore.thesis.students.application.port.in.command.ChangeInterestCommand;
import com.devsxplore.thesis.students.domain.model.Student;

public interface RemoveInterestUseCase {

	Student removeInterest(ChangeInterestCommand command);

}
