package com.devsxplore.thesis.profiles.domain.model;

public class Topic {
    private final TopicId id;
    private final String title;
    private final String description;

    private Topic(TopicId id, String title, String description) {
        if (id == null) {
            throw new IllegalArgumentException("");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("dawda");
        }
        if (description == null || description.isBlank()) {
            description = "";
        }

        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id.id();
    }

    public String getDescription() {
        return description;
    }

    public static Topic createTopicWithoutId(String title, String description){
        return new Topic(TopicId.unassigned(),title, description);
    }

    public static Topic createTopicWithId(TopicId id, String title, String description){
        return new Topic(id, title, description);
    }
}
