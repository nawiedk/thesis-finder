package com.devsxplore.thesis.supervisors.domain.model;

public record TopicId(Long topicId) {
	public TopicId {
		if (topicId != null && topicId <= 0) {
			throw new IllegalArgumentException("Id must be positive");
		}
	}

	public static TopicId unassigned() {
		return new TopicId(null);
	}
}
