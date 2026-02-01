package com.devsxplore.thesis.profiles.application.port.in.usecase.topic;

import com.devsxplore.thesis.profiles.application.port.in.command.topic.CreateTopicCommand;
import com.devsxplore.thesis.profiles.domain.model.Topic;

public interface TopicCreateUseCase {
    Topic createTopic(CreateTopicCommand command);
}
