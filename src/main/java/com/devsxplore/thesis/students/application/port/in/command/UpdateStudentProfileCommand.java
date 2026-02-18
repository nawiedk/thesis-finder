package com.devsxplore.thesis.students.application.port.in.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateStudentProfileCommand(@NotNull @Positive(message = "User ID muss positiv sein") Long studentUserId,

		@NotBlank String firstName,

		@NotBlank String lastName) {
}
