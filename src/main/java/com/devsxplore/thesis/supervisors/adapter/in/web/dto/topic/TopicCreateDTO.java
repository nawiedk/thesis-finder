package com.devsxplore.thesis.supervisors.adapter.in.web.dto.topic;

import jakarta.validation.constraints.NotBlank;

public record TopicCreateDTO(@NotBlank Long supervisorId, @NotBlank String title, @NotBlank String description) {
}
