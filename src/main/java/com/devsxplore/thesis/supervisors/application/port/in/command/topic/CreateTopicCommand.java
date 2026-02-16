package com.devsxplore.thesis.supervisors.application.port.in.command.topic;

public record CreateTopicCommand(Long supervisorUserId, String title, String description) {
    public CreateTopicCommand {
        if (supervisorUserId == null || supervisorUserId < 1) {
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
