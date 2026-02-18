package com.devsxplore.thesis.students.application.port.in.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LoadStudentByUserIdCommand(@NotNull @Positive(message = "User ID muss positiv sein") Long studentUserId) {

}
