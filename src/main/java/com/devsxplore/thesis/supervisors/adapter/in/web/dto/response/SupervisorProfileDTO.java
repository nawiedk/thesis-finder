package com.devsxplore.thesis.supervisors.adapter.in.web.dto.response;

import java.util.UUID;

public record SupervisorProfileDTO(
        UUID uuid,
        String fullName,
        String Email,
        String office,
        String phone
) {
}