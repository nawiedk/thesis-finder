package com.devsxplore.thesis.profiles.application.port.in.command.topic;

public record SaveChangesTopicCommand(Long supervisorId, Long topicId, String title, String description) {
}
