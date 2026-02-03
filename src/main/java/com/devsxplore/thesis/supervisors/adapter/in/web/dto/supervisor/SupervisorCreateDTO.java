package com.devsxplore.thesis.supervisors.adapter.in.web.dto.supervisor;

import jakarta.validation.constraints.NotBlank;

public record SupervisorCreateDTO(
        @NotBlank
        String firstName,
        String lastName,
        String title,
        String email,
        String office,
        String phone
) {}
