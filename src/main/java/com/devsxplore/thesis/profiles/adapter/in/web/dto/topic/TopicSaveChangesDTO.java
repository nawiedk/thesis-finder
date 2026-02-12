package com.devsxplore.thesis.profiles.adapter.in.web.dto.topic;

import jakarta.validation.constraints.NotBlank;

public record TopicSaveChangesDTO(
        Long supervisorId,
        Long topicId,
        @NotBlank(message = "Titel darf nicht leer sein.")
        String title,
        String description) {
}
