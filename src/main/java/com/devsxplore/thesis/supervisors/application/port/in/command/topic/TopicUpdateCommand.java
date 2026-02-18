package com.devsxplore.thesis.supervisors.application.port.in.command.topic;

public record TopicUpdateCommand(Long supervisorUserId, Long topicId, String title, String description) {
	public TopicUpdateCommand {
		if (supervisorUserId == null || supervisorUserId < 1) {
			throw new IllegalArgumentException("Id must be positive");
		}
	}
}
