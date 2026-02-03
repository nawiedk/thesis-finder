package com.devsxplore.thesis.supervisors.application.port.in.usecase.topic;

import com.devsxplore.thesis.supervisors.application.port.in.command.topic.CreateTopicCommand;
import com.devsxplore.thesis.supervisors.domain.model.Topic;

public interface TopicCreateUseCase {
    Topic createTopic(CreateTopicCommand command);
}
