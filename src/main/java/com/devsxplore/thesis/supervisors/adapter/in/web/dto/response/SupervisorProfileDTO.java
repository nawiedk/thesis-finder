package com.devsxplore.thesis.supervisors.adapter.in.web.dto.response;

import java.util.Set;
import java.util.UUID;

public record SupervisorProfileDTO(UUID publicId, String fullName, String email, String office, String phone,
								   Set<String> fields) {
}
