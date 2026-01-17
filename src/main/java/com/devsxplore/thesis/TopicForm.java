package com.devsxplore.thesis;

import jakarta.validation.constraints.NotNull;

public class TopicForm {

    @NotNull
    private final String topic;
    private final String description;

    public TopicForm(String topic, String description) {
        this.topic = topic;
        this.description = description;
    }

    public String getTopic() {
        return topic;
    }

    public String getDescription() {
        return description;
    }
}
