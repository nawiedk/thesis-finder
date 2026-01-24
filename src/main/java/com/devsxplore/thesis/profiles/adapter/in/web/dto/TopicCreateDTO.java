package com.devsxplore.thesis.profiles.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record TopicCreateDTO (
        @NotBlank
        String topic,
        @NotBlank
        String description
){}
