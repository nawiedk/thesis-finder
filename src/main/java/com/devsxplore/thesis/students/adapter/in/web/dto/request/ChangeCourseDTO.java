package com.devsxplore.thesis.students.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ChangeCourseDTO(
        @NotBlank(message = "Kursname darf nicht leer sein")
        @Pattern(regexp = "^[A-Za-z0-9\\s\\-äöüÄÖÜ]+$", message = "Kursname enthält ungültige Zeichen")
        @Size(max = 100, message = "Kursname ist zu lang")
        String course
) {
}
