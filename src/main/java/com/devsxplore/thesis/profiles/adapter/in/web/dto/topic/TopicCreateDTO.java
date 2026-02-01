package com.devsxplore.thesis.profiles.adapter.in.web.dto.topic;

import jakarta.validation.constraints.NotBlank;

public record TopicCreateDTO (
        @NotBlank
        Long supervisorId,
        @NotBlank
        String title,
        @NotBlank
        String description
){}
