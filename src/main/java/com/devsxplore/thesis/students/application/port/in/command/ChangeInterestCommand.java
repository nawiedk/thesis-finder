package com.devsxplore.thesis.students.application.port.in.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ChangeInterestCommand(
        @NotNull @Positive(message = "User ID muss positiv sein")
        Long studentUserId,

        @NotBlank @Size(max = 50)
        String interest
) {
}
