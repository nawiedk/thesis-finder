package com.devsxplore.thesis.students.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangeInterestDTO(
        @NotBlank(message = "Interesse darf nicht leer sein")
        @Size(min = 3, max = 50, message = "Interesse sollte prägnant sein (3-50 Zeichen)")
        String interest
) {
}
