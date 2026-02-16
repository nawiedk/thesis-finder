package com.devsxplore.thesis.students.application.port.in.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public record RegisterStudentCommand(
        @NotNull @Positive(message = "User ID muss positiv sein")
        Long studentUserId,

        @NotBlank @Size(min = 2)
        String firstName,

        @NotBlank @Size(min = 2)
        String lastName
) {
    public RegisterStudentCommand {
        Objects.requireNonNull(studentUserId);
    }
}
