package com.devsxplore.thesis.students.application.port.in.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LoadStudentCommand(@NotNull @Positive(message = "Student ID muss positiv sein") Long studentId) {
}
