package com.devsxplore.thesis.profiles.domain.model;

import java.util.Set;

@SuppressWarnings("LombokGetterMayBeUsed")
public class Topic {
    private final TopicId topicId;
    private String title;
    private String description;
    private Set<Link> links;
    private Set<FieldTag> fields;

    private Topic(TopicId topicId, String title, String description) {
        if (topicId == null) {
            throw new IllegalArgumentException("Topic ID cannot be null");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be blank");
        }
        if (description == null || description.isBlank()) {
            description = "";
        }
        this.topicId = topicId;
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

    public Set<Link> addLinksToTopic(Set<Link> links){
        this.links.addAll(links);
        return Set.copyOf(this.links);
    }

    public Set<Link> addFieldToTopic(Set<FieldTag> fields){
        this.fields.addAll(fields);
        return Set.copyOf(this.links);
    }
}
