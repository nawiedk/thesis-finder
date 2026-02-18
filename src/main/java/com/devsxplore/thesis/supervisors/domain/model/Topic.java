package com.devsxplore.thesis.supervisors.domain.model;

import java.util.Objects;

@SuppressWarnings("LombokGetterMayBeUsed")
public class Topic {

	private final TopicId topicId;

	private String title;

	private String description;

	private Topic(TopicId topicId, String title, String description) {
		this.topicId = Objects.requireNonNull(topicId, "Topic ID cannot be null");

		if (title == null || title.isBlank()) {
			throw new IllegalArgumentException("Title cannot be blank");
		}
		if (description == null || description.isBlank()) {
			description = "";
		}
		this.title = title;
		this.description = description;
	}

	public static Topic createTopicWithoutId(String title, String description) {
		return new Topic(TopicId.unassigned(), title, description);
	}

	public static Topic createTopicWithId(TopicId topicId, String title, String description) {
		return new Topic(topicId, title, description);
	}

	public Long getTopicId() {
		return topicId.topicId();
	}

	public String getDescription() {
		return description;
	}

	public String getTitle() {
		return title;
	}

	public Topic updateTopic(String newTitle, String newDescription) {
		title = newTitle == null ? title : newTitle;
		description = newDescription == null ? description : newDescription;
		return this;
	}

}
