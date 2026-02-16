package com.devsxplore.thesis.students.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterStudentDTO(
        @NotBlank(message = "Vorname darf nicht leer sein")
        @Size(min = 2, max = 50, message = "Vorname muss zwischen {min} und {max} Zeichen lang sein")
        String firstName,

        @NotBlank(message = "Nachname darf nicht leer sein")
        @Size(min = 2, max = 50, message = "Nachname muss zwischen {min} und {max} Zeichen lang sein")
        String lastName
) {
}
