package com.devsxplore.thesis.supervisors.application.port.in.command.topic;

public record SaveChangesTopicCommand(Long supervisorUserId, Long topicId, String title, String description) {
}
