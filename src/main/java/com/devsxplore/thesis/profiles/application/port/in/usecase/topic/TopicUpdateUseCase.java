package com.devsxplore.thesis.profiles.application.port.in.usecase.topic;

import com.devsxplore.thesis.profiles.application.port.in.command.topic.TopicUpdateCommand;
import com.devsxplore.thesis.profiles.domain.model.Topic;

public interface TopicUpdateUseCase {
    Topic updateTopic(TopicUpdateCommand command);
}
