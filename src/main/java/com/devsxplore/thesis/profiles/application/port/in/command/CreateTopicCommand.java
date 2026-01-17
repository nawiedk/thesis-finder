package com.devsxplore.thesis.profiles.application.port.in.command;

public record CreateTopicCommand(Long supervisorId, String title, String description) {
    public CreateTopicCommand {
        if (supervisorId == null || supervisorId < 1) {
            throw new IllegalArgumentException("Id must be positive");
        }
        if (title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
        if (description.isBlank()) {
            description = "";
        }
        title = title.trim();
        description = description.trim();
    }
}
