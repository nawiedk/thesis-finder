package com.devsxplore.thesis.profiles.application.port.in.usecase.topic;

import com.devsxplore.thesis.profiles.domain.model.Topic;

import java.util.List;

public interface TopicLoadUseCase {
    List<Topic> loadAllTopics();
}
