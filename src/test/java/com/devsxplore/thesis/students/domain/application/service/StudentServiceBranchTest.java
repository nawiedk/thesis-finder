package com.devsxplore.thesis.students.domain.application.service;

import com.devsxplore.thesis.accounts.application.port.in.usecase.AssignUserRoleUseCase;
import com.devsxplore.thesis.students.application.port.in.command.ChangeCourseCommand;
import com.devsxplore.thesis.students.application.port.in.command.RegisterStudentCommand;
import com.devsxplore.thesis.students.application.port.out.StudentRepositoryPort;
import com.devsxplore.thesis.students.application.service.StudentService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StudentServiceBranchTest {

    private final StudentRepositoryPort repository = mock(StudentRepositoryPort.class);
    private final AssignUserRoleUseCase assignRoleUseCase = mock(AssignUserRoleUseCase.class);
    private final StudentService service = new StudentService(repository, assignRoleUseCase);

    @Test
    void registerStudent_should_throw_exception_if_already_exists() {
        Long userId = 1L;
        RegisterStudentCommand command = new RegisterStudentCommand(userId, "Max", "Mustermann");
        when(repository.existsByStudentUserId(userId)).thenReturn(true);

        assertThatThrownBy(() -> service.registerStudent(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Du bist bereits registriert!");
    }

    @Test
    void addCourse_should_throw_exception_if_student_not_found() {
        ChangeCourseCommand command = new ChangeCourseCommand(1L, "Java");
        when(repository.loadByStudentUserId(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.addCourse(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Student not found");
    }
}